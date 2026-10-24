package com.example.cegct.features.notifications

import android.util.Log
import com.example.cegct.core.network.NetworkClient
import com.example.cegct.core.network.NotificationDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class NotificationsRepository {

    private val _notifications = MutableStateFlow<List<NotificationDto>>(emptyList())
    val notifications: StateFlow<List<NotificationDto>> = _notifications

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _unreadCount = MutableStateFlow(0)
    val unreadCount: StateFlow<Int> = _unreadCount

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        loadNotifications()
        setupSocketListener()
    }

    fun loadNotifications() {
        scope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val bearer = NetworkClient.getBearerToken()
                if (bearer.isNotBlank()) {
                    val response = NetworkClient.apiService.getNotifications(bearer)
                    if (response.isSuccessful && response.body()?.data != null) {
                        val fetchedList = response.body()!!.data!!
                        _notifications.value = fetchedList
                        updateUnreadCount(fetchedList)
                    }
                }
            } catch (e: Exception) {
                Log.e("CEGCT_NOTIF", "Failed to fetch notifications: ${e.message}")
                _error.value = e.message ?: "Failed to load notifications"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun setupSocketListener() {
        try {
            val token = NetworkClient.authToken.orEmpty()
            if (token.isNotBlank()) {
                val socket = NetworkClient.connectSocket(token)
                socket.off("notification:new")
                socket.on("notification:new") { args ->
                    if (args.isNotEmpty()) {
                        try {
                            val dataStr = args[0].toString()
                            val json = JSONObject(dataStr)
                            val newNotif = NotificationDto(
                                id = json.optString("id").ifBlank { json.optString("_id") },
                                reportId = json.optString("report_id").ifBlank { json.optString("reportId") },
                                type = json.optString("type"),
                                title = json.optString("title"),
                                body = json.optString("body").ifBlank { json.optString("message") },
                                isReadSnake = json.optBoolean("is_read", false),
                                isReadCamel = json.optBoolean("isRead", false),
                                createdAtSnake = json.optString("created_at").ifBlank { json.optString("createdAt") }
                            )
                            mergeNotification(newNotif)
                        } catch (e: Exception) {
                            Log.e("CEGCT_NOTIF", "Error parsing socket notification: ${e.message}")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("CEGCT_NOTIF", "Socket listener error: ${e.message}")
        }
    }

    private fun mergeNotification(notif: NotificationDto) {
        val current = _notifications.value.toMutableList()
        val exists = current.any { it.effectiveId == notif.effectiveId }
        if (!exists) {
            current.add(0, notif)
            _notifications.value = current
            updateUnreadCount(current)
        }
    }

    private fun updateUnreadCount(list: List<NotificationDto>) {
        _unreadCount.value = list.count { !it.isRead }
    }

    fun markAsRead(notificationId: String, onComplete: () -> Unit = {}) {
        if (notificationId.isBlank()) {
            onComplete()
            return
        }

        // Optimistically mark as read locally
        val current = _notifications.value.toMutableList()
        val index = current.indexOfFirst { it.effectiveId == notificationId }
        if (index != -1) {
            current[index] = current[index].copy(isReadSnake = true, isReadCamel = true)
            _notifications.value = current
            updateUnreadCount(current)
        }

        scope.launch {
            try {
                val bearer = NetworkClient.getBearerToken()
                if (bearer.isNotBlank()) {
                    NetworkClient.apiService.markNotificationRead(bearer, notificationId)
                }
            } catch (e: Exception) {
                Log.e("CEGCT_NOTIF", "Failed to mark notification $notificationId as read: ${e.message}")
            } finally {
                onComplete()
            }
        }
    }
}
