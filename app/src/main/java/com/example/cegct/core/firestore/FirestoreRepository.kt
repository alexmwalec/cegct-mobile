package com.example.cegct.core.firestore

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.cegct.core.firestore.models.AnnouncementEntity
import com.example.cegct.core.firestore.models.ReportEntity
import com.example.cegct.core.firestore.models.UserEntity
import com.example.cegct.core.network.NetworkClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.UUID

interface FirestoreRepository {
    suspend fun saveUserProfile(user: UserEntity): Result<Unit>
    suspend fun getUserProfile(uid: String): Result<UserEntity?>
    suspend fun updateFcmToken(uid: String, token: String): Result<Unit>

    suspend fun createReport(
        report: ReportEntity,
        imageUri: Uri? = null,
        context: Context? = null
    ): Result<ReportEntity>

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

    override suspend fun createReport(
        report: ReportEntity,
        imageUri: Uri?,
        context: Context?
    ): Result<ReportEntity> = runCatching {
        val currentUser = NetworkClient.currentUser
        val tempId = report.reportId.ifEmpty { "REP-${UUID.randomUUID().toString().take(8)}" }

        // 1. Prepare image multipart if imageUri is provided
        var imagePart: MultipartBody.Part? = null
        if (imageUri != null && context != null) {
            val prepResult = createImagePart(context, imageUri)
            if (prepResult.isFailure) {
                val err = prepResult.exceptionOrNull() ?: Exception("Image preparation failed")
                throw err
            }
            imagePart = prepResult.getOrNull()
        }

        // 2. Build multipart parts list
        val parts = mutableListOf<MultipartBody.Part>()

        val categoryVal = report.category.ifBlank { "Illegal dumping" }
        parts.add(MultipartBody.Part.createFormData("category", categoryVal))

        if (report.description.isNotBlank()) {
            parts.add(MultipartBody.Part.createFormData("description", report.description))
        }

        val mappedUrgency = when (report.urgency.uppercase()) {
            "LOW" -> "LOW"
            "HIGH" -> "HIGH"
            "CRITICAL" -> "CRITICAL"
            else -> "MEDIUM"
        }
        parts.add(MultipartBody.Part.createFormData("urgency", mappedUrgency))
        parts.add(MultipartBody.Part.createFormData("latitude", report.latitude.toString()))
        parts.add(MultipartBody.Part.createFormData("longitude", report.longitude.toString()))

        val titleVal = report.title.ifBlank { categoryVal }
        parts.add(MultipartBody.Part.createFormData("title", titleVal))

        val addressVal = report.address.ifBlank { "Area 25 Sector 3, Lilongwe" }
        parts.add(MultipartBody.Part.createFormData("address", addressVal))

        if (imagePart != null) {
            parts.add(imagePart)
        }

        // Sanitized Logging: Log request URL and field names (NO passwords, tokens, or secrets)
        val requestUrl = NetworkClient.BASE_URL + "reports"
        Log.d("CEGCT_API", "POST Request URL: $requestUrl")
        Log.d("CEGCT_API", "Sanitized Request Multipart Fields: category='$categoryVal', title='$titleVal', urgency='$mappedUrgency', latitude=${report.latitude}, longitude=${report.longitude}, address='$addressVal', hasImage=${imagePart != null}")

        val bearer = NetworkClient.getBearerToken()
        val response = NetworkClient.apiService.createReport(bearer, parts)

        Log.d("CEGCT_API", "Response HTTP Status: ${response.code()}")

        if (response.isSuccessful && response.body()?.data != null) {
            val createdDto = response.body()!!.data!!
            Log.d("CEGCT_API", "Response Body: { id: '${createdDto.id}', photoUrl: '${createdDto.photoUrl}', category: '${createdDto.category}', status: '${createdDto.status}' }")

            val finalReportId = createdDto.id.ifBlank { tempId }
            val photoUrlResult = createdDto.photoUrl
            val finalImageUrls = if (!photoUrlResult.isNullOrBlank()) listOf(photoUrlResult) else emptyList()

            val createdEntity = report.copy(
                reportId = finalReportId,
                userId = currentUser?.id ?: report.userId.ifEmpty { "usr-001" },
                userName = currentUser?.name ?: report.userName.ifEmpty { "Citizen" },
                userEmail = currentUser?.email ?: report.userEmail,
                status = createdDto.status.ifBlank { report.status },
                imageUrls = finalImageUrls,
                createdAt = report.createdAt ?: System.currentTimeMillis()
            )

            // Update local state
            reportsState.value = listOf(createdEntity) + reportsState.value.filter { it.reportId != tempId && it.reportId != finalReportId }

            // Join Socket.IO report room with the real API-generated Report ID
            NetworkClient.joinReportRoom(finalReportId)

            // Verify GET /api/reports/:id using the same token
            try {
                val getRes = NetworkClient.apiService.getReportById(bearer, finalReportId)
                Log.d("CEGCT_API", "GET /api/reports/$finalReportId HTTP Status: ${getRes.code()}")
                if (getRes.isSuccessful && getRes.body()?.data != null) {
                    Log.d("CEGCT_API", "GET /api/reports/$finalReportId Response Confirmed: ${getRes.body()?.data?.title}")
                }
            } catch (e: Exception) {
                Log.e("CEGCT_API", "GET /api/reports/$finalReportId error: ${e.message}")
            }

            createdEntity
        } else {
            val statusCode = response.code()
            val rawErrorBody = response.errorBody()?.string() ?: ""
            Log.e("CEGCT_API", "Response Error ($statusCode): $rawErrorBody")

            val errorMessage = when (statusCode) {
                413 -> "Image size exceeds maximum limit of 10 MB."
                415 -> "Unsupported image format. Please select a JPEG, PNG, or WebP image."
                502 -> "Cloudinary upload failed on the server."
                503 -> "Cloudinary is not configured on the deployed API."
                else -> {
                    parseErrorMessage(rawErrorBody) ?: "Report submission failed (HTTP $statusCode)."
                }
            }
            throw Exception(errorMessage)
        }
    }

    private fun createImagePart(context: Context, uri: Uri): Result<MultipartBody.Part> {
        return try {
            val contentResolver = context.contentResolver
            var mimeType = contentResolver.getType(uri)
            if (mimeType.isNullOrBlank()) {
                val uriStr = uri.toString().lowercase()
                mimeType = when {
                    uriStr.endsWith(".png") -> "image/png"
                    uriStr.endsWith(".webp") -> "image/webp"
                    else -> "image/jpeg"
                }
            }

            val normalizedMime = mimeType.lowercase()
            if (!normalizedMime.contains("jpeg") &&
                !normalizedMime.contains("jpg") &&
                !normalizedMime.contains("png") &&
                !normalizedMime.contains("webp")) {
                return Result.failure(Exception("Unsupported image format. Please select a JPEG, PNG, or WebP image."))
            }

            val inputStream = contentResolver.openInputStream(uri)
                ?: return Result.failure(Exception("Could not read selected image file."))

            val bytes = inputStream.use { it.readBytes() }

            // Maximum size: 10 MB = 10,485,760 bytes
            val maxSizeBytes = 10 * 1024 * 1024
            if (bytes.size > maxSizeBytes) {
                return Result.failure(Exception("Image size exceeds maximum limit of 10 MB."))
            }

            val fileName = when {
                normalizedMime.contains("png") -> "report_image.png"
                normalizedMime.contains("webp") -> "report_image.webp"
                else -> "report_image.jpg"
            }

            val mediaType = mimeType.toMediaTypeOrNull() ?: "image/jpeg".toMediaTypeOrNull()
            val requestBody = bytes.toRequestBody(mediaType)
            val part = MultipartBody.Part.createFormData("image", fileName, requestBody)

            Result.success(part)
        } catch (e: Exception) {
            Result.failure(Exception("Error processing image: ${e.message}"))
        }
    }

    private fun parseErrorMessage(rawJson: String): String? {
        return try {
            if (rawJson.isBlank()) return null
            val json = JSONObject(rawJson)
            json.optString("error").ifBlank { json.optString("message").ifBlank { null } }
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun getReportDetails(reportId: String): Result<ReportEntity?> = runCatching {
        reportsState.value.firstOrNull { it.reportId == reportId }
    }

    override fun observeUserReports(userId: String): Flow<List<ReportEntity>> {
        return reportsState.map { list ->
            val currentUser = NetworkClient.currentUser
            val activeUserId = currentUser?.id?.ifBlank { null } ?: userId.ifBlank { null }
            val activeEmail = currentUser?.email?.ifBlank { null }

            if (activeUserId == null && activeEmail == null) {
                emptyList()
            } else {
                list.filter { report ->
                    (activeUserId != null && report.userId == activeUserId) ||
                    (activeEmail != null && report.userEmail.isNotBlank() && report.userEmail.equals(activeEmail, ignoreCase = true))
                }
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
