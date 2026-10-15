package com.example.cegct.core.firestore

import android.util.Log
import com.example.cegct.core.firestore.models.AnnouncementEntity
import com.example.cegct.core.firestore.models.ReportEntity
import com.example.cegct.core.firestore.models.UserEntity
import com.example.cegct.core.network.CreateReportRequest
import com.example.cegct.core.network.NetworkClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.UUID

interface FirestoreRepository {
    suspend fun saveUserProfile(user: UserEntity): Result<Unit>
    suspend fun getUserProfile(uid: String): Result<UserEntity?>
    suspend fun updateFcmToken(uid: String, token: String): Result<Unit>

    suspend fun createReport(report: ReportEntity): Result<String>
    suspend fun getReportDetails(reportId: String): Result<ReportEntity?>
    fun observeUserReports(userId: String): Flow<List<ReportEntity>>
    fun observeAllReports(): Flow<List<ReportEntity>>

    fun observeAnnouncements(): Flow<List<AnnouncementEntity>>
}

class FirestoreRepositoryImpl : FirestoreRepository {

    private val users = mutableMapOf<String, UserEntity>()
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        setupSocketListener()
    }

    private fun setupSocketListener() {
        try {
            val token = NetworkClient.authToken
            if (!token.isNullOrEmpty()) {
                val socket = NetworkClient.connectSocket(token)

                // Handle status updates from Web Admin via Socket.IO
                val statusHandler = { args: Array<out Any> ->
                    if (args.isNotEmpty()) {
                        try {
                            val dataStr = args[0].toString()
                            val json = JSONObject(dataStr)
                            val updatedId = json.optString("id").ifBlank { json.optString("reportId") }
                            val updatedStatus = json.optString("status")
                            if (updatedId.isNotBlank() && updatedStatus.isNotBlank()) {
                                updateReportStatus(updatedId, updatedStatus)
                            }
                        } catch (e: Exception) {
                            Log.e("CEGCT_API", "Error parsing socket status update: ${e.message}")
                        }
                    }
                }

                socket.on("report:status_changed") { statusHandler(it) }
                socket.on("report:updated") { statusHandler(it) }

                // Refetch reports on socket reconnect so missed events do not leave stale status
                socket.on(io.socket.client.Socket.EVENT_CONNECT) {
                    refetchUserReports()
                }
            }
        } catch (e: Exception) {
            Log.e("CEGCT_API", "Socket setup error: ${e.message}")
        }
    }

    private fun updateReportStatus(reportId: String, newStatus: String) {
        val currentList = reportsState.value.toMutableList()
        val index = currentList.indexOfFirst {
            it.reportId == reportId || it.reportId.endsWith(reportId) || reportId.endsWith(it.reportId)
        }
        if (index != -1) {
            val updated = currentList[index].copy(
                status = newStatus,
                updatedAt = System.currentTimeMillis()
            )
            currentList[index] = updated
            reportsState.value = currentList
            Log.d("CEGCT_API", "Updated report status for ID '$reportId' to '$newStatus'")
        }
    }

    private fun refetchUserReports() {
        scope.launch {
            try {
                val bearer = NetworkClient.getBearerToken()
                if (bearer.isNotBlank()) {
                    val res = NetworkClient.apiService.getReports(bearer)
                    if (res.isSuccessful && res.body()?.data != null) {
                        val dtoList = res.body()!!.data!!
                        val updatedList = dtoList.map { dto ->
                            ReportEntity(
                                reportId = dto.id,
                                userId = NetworkClient.currentUser?.id ?: "usr-001",
                                userName = NetworkClient.currentUser?.name ?: "Citizen",
                                userEmail = NetworkClient.currentUser?.email ?: "",
                                title = dto.title ?: dto.category,
                                description = dto.description ?: "",
                                category = dto.category,
                                status = dto.status,
                                urgency = dto.severity.ifBlank { "MEDIUM" },
                                latitude = dto.latitude ?: -13.9350,
                                longitude = dto.longitude ?: 33.7850,
                                address = dto.location.ifBlank { "Lilongwe" },
                                imageUrls = listOfNotNull(dto.photoUrl).filter {
                                    it.startsWith("http://") || it.startsWith("https://")
                                },
                                createdAt = System.currentTimeMillis()
                            )
                        }
                        reportsState.value = updatedList
                        Log.d("CEGCT_API", "Refetched ${updatedList.size} reports on socket reconnect.")
                    }
                }
            } catch (e: Exception) {
                Log.e("CEGCT_API", "Failed to refetch reports on reconnect: ${e.message}")
            }
        }
    }

    override suspend fun saveUserProfile(user: UserEntity): Result<Unit> = runCatching {
        users[user.uid] = user
    }

    override suspend fun getUserProfile(uid: String): Result<UserEntity?> = runCatching {
        users[uid]
    }

    override suspend fun updateFcmToken(uid: String, token: String): Result<Unit> = runCatching {
        val existing = users[uid]
        if (existing != null) {
            users[uid] = existing.copy(fcmToken = token, updatedAt = System.currentTimeMillis())
        }
    }

    override suspend fun createReport(report: ReportEntity): Result<String> = runCatching {
        val currentUser = NetworkClient.currentUser
        val tempId = report.reportId.ifEmpty { "REP-${UUID.randomUUID().toString().take(8)}" }
        var finalReportId = tempId

        val newReport = report.copy(
            reportId = tempId,
            userId = currentUser?.id ?: report.userId.ifEmpty { "usr-001" },
            userName = currentUser?.name ?: report.userName.ifEmpty { "Citizen" },
            userEmail = currentUser?.email ?: report.userEmail,
            createdAt = report.createdAt ?: System.currentTimeMillis()
        )
        reportsState.value = listOf(newReport) + reportsState.value

        try {
            // Only send valid public HTTPS URLs for evidence photos
            val validPhotoUrl = report.imageUrls.firstOrNull()?.takeIf {
                it.startsWith("http://") || it.startsWith("https://")
            }

            val req = CreateReportRequest(
                category = report.category.ifBlank { "Illegal dumping" },
                title = report.title.ifBlank { report.category.ifBlank { "Environmental Incident" } },
                description = report.description,
                urgency = report.urgency.ifBlank { "MEDIUM" },
                latitude = report.latitude,
                longitude = report.longitude,
                address = report.address.ifBlank { "Area 25 Sector 3, Lilongwe" },
                photoUrl = validPhotoUrl
            )

            // Sanitized Logging: Log request URL and fields (NO passwords, tokens, or secrets)
            val requestUrl = NetworkClient.BASE_URL + "reports"
            Log.d("CEGCT_API", "POST Request URL: $requestUrl")
            Log.d("CEGCT_API", "Sanitized Request Payload: category='${req.category}', title='${req.title}', urgency='${req.urgency}', lat=${req.latitude}, lng=${req.longitude}, address='${req.address}', photoUrl='${req.photoUrl}'")

            val bearer = NetworkClient.getBearerToken()
            val response = NetworkClient.apiService.createReport(bearer, req)

            Log.d("CEGCT_API", "Response HTTP Status: ${response.code()}")

            if (response.isSuccessful && response.body()?.data != null) {
                val createdDto = response.body()!!.data!!
                Log.d("CEGCT_API", "Response Body: { id: '${createdDto.id}', category: '${createdDto.category}', status: '${createdDto.status}' }")

                if (createdDto.id.isNotBlank()) {
                    finalReportId = createdDto.id

                    // Update local state to use the real API-generated Report ID!
                    val updatedEntity = newReport.copy(
                        reportId = createdDto.id,
                        status = createdDto.status.ifBlank { newReport.status }
                    )
                    reportsState.value = listOf(updatedEntity) + reportsState.value.filter { it.reportId != tempId }

                    // Join Socket.IO report room with the real API-generated Report ID
                    NetworkClient.joinReportRoom(createdDto.id)

                    // Verify GET /api/reports/:id using the same token
                    try {
                        val getRes = NetworkClient.apiService.getReportById(bearer, createdDto.id)
                        Log.d("CEGCT_API", "GET /api/reports/${createdDto.id} HTTP Status: ${getRes.code()}")
                        if (getRes.isSuccessful && getRes.body()?.data != null) {
                            Log.d("CEGCT_API", "GET /api/reports/${createdDto.id} Response Confirmed: ${getRes.body()?.data?.title}")
                        }
                    } catch (e: Exception) {
                        Log.e("CEGCT_API", "GET /api/reports/${createdDto.id} error: ${e.message}")
                    }
                }
            } else {
                val rawError = response.errorBody()?.string() ?: ""
                Log.e("CEGCT_API", "Response Error Body: $rawError")
            }
        } catch (e: Exception) {
            Log.e("CEGCT_API", "Network exception when posting report: ${e.message}", e)
        }

        finalReportId
    }

    override suspend fun getReportDetails(reportId: String): Result<ReportEntity?> = runCatching {
        reportsState.value.firstOrNull { it.reportId == reportId }
    }

    override fun observeUserReports(userId: String): Flow<List<ReportEntity>> {
        return reportsState.map { list ->
            val curId = NetworkClient.currentUser?.id
            if (!curId.isNullOrEmpty()) {
                list.filter { it.userId == curId || it.userId == userId || userId.isBlank() }
            } else {
                list
            }
        }
    }

    override fun observeAllReports(): Flow<List<ReportEntity>> {
        return reportsState
    }

    override fun observeAnnouncements(): Flow<List<AnnouncementEntity>> {
        return announcementsState
    }

    companion object {
        private val reportsState = MutableStateFlow<List<ReportEntity>>(emptyList())
        private val announcementsState = MutableStateFlow(
            listOf(
                AnnouncementEntity(
                    announcementId = "ann-1",
                    title = "Community Clean-up Drive",
                    content = "Join us this weekend for the community clean-up drive in Lilongwe.",
                    category = "COMMUNITY",
                    authorName = "CEGCT Team",
                    isImportant = true,
                    createdAt = System.currentTimeMillis(),
                )
            )
        )
    }
}
