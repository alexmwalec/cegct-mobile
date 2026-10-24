package com.example.cegct.features.chat

import android.util.Log
import com.example.cegct.core.network.ChatMessageDto
import com.example.cegct.core.network.NetworkClient
import com.example.cegct.core.network.SendMessageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class ChatRepository(private val reportId: String) {

    private val _messages = MutableStateFlow<List<ChatMessageDto>>(emptyList())
    val messages: StateFlow<List<ChatMessageDto>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _sendError = MutableStateFlow<String?>(null)
    val sendError: StateFlow<String?> = _sendError

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        loadMessages()
        setupSocketListener()
    }

    fun loadMessages() {
        if (reportId.isBlank()) return
        scope.launch {
            _isLoading.value = true
            try {
                val bearer = NetworkClient.getBearerToken()
                if (bearer.isNotBlank()) {
                    val response = NetworkClient.apiService.getReportMessages(bearer, reportId)
                    if (response.isSuccessful && response.body()?.data != null) {
                        val fetchedList = response.body()!!.data!!
                        mergeMessages(fetchedList)
                    }
                }
            } catch (e: Exception) {
                Log.e("CEGCT_CHAT", "Failed to load messages for $reportId: ${e.message}")
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
                NetworkClient.joinReportRoom(reportId)

                socket.off("chat:new_message")
                socket.on("chat:new_message") { args ->
                    if (args.isNotEmpty()) {
                        try {
                            val dataStr = args[0].toString()
                            val json = JSONObject(dataStr)
                            val incomingReportId = json.optString("report_id").ifBlank { json.optString("reportId") }
                            if (incomingReportId.isBlank() || incomingReportId == reportId || incomingReportId.endsWith(reportId) || reportId.endsWith(incomingReportId)) {
                                val newMsg = ChatMessageDto(
                                    id = json.optString("id").ifBlank { json.optString("_id") },
                                    reportId = incomingReportId,
                                    senderId = json.optString("sender_id").ifBlank { json.optString("senderId") },
                                    senderName = json.optString("sender_name").ifBlank { json.optString("senderName") },
                                    senderRole = json.optString("sender_role").ifBlank { json.optString("senderRole") },
                                    message = json.optString("message").ifBlank { json.optString("text") },
                                    sentAt = json.optString("sent_at").ifBlank { json.optString("createdAt") }
                                )
                                mergeMessages(listOf(newMsg))
                            }
                        } catch (e: Exception) {
                            Log.e("CEGCT_CHAT", "Error parsing socket new_message: ${e.message}")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("CEGCT_CHAT", "Socket error in ChatRepository: ${e.message}")
        }
    }

    fun sendMessage(text: String, onComplete: (Boolean) -> Unit = {}) {
        if (text.isBlank() || reportId.isBlank()) return
        scope.launch {
            _sendError.value = null
            try {
                val bearer = NetworkClient.getBearerToken()
                val response = NetworkClient.apiService.sendMessage(bearer, reportId, SendMessageRequest(text))
                if (response.isSuccessful) {
                    val created = response.body()?.data ?: ChatMessageDto(
                        reportId = reportId,
                        senderName = NetworkClient.currentUser?.name ?: "Citizen",
                        senderRole = "citizen",
                        message = text,
                        sentAt = "Just now"
                    )
                    mergeMessages(listOf(created))
                    onComplete(true)
                } else {
                    val err = "Failed to send message (${response.code()})"
                    _sendError.value = err
                    onComplete(false)
                }
            } catch (e: Exception) {
                _sendError.value = e.message ?: "Failed to send message"
                onComplete(false)
            }
        }
    }

    private fun mergeMessages(newList: List<ChatMessageDto>) {
        val current = _messages.value.toMutableList()
        var updated = false
        newList.forEach { item ->
            val idMatches = current.any { existing ->
                existing.effectiveId == item.effectiveId ||
                (existing.effectiveMessage == item.effectiveMessage && existing.effectiveSenderName == item.effectiveSenderName && existing.effectiveSentAt == item.effectiveSentAt)
            }
            if (!idMatches && item.effectiveMessage.isNotBlank()) {
                current.add(item)
                updated = true
            }
        }
        if (updated || _messages.value.isEmpty()) {
            _messages.value = current
        }
    }
}
