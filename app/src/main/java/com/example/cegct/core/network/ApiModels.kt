package com.example.cegct.core.network

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("phone") val phone: String
)

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class UserDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("role") val role: String,
    @SerializedName("phone") val phone: String? = null
)

data class AuthResponse(
    @SerializedName("token") val token: String,
    @SerializedName("user") val user: UserDto
)

data class CreateReportRequest(
    @SerializedName("category") val category: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String? = null,
    @SerializedName("urgency") val urgency: String = "MEDIUM",
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("address") val address: String? = null,
    @SerializedName("photoUrl") val photoUrl: String? = null
)

data class ReportResponseDto(
    @SerializedName("id") val id: String,
    @SerializedName("category") val category: String,
    @SerializedName("severity") val severity: String,
    @SerializedName("status") val status: String,
    @SerializedName("location") val location: String,
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?,
    @SerializedName("date") val date: String?,
    @SerializedName("reporter") val reporter: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("photoUrl") val photoUrl: String?
)

data class ChatMessageDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("_id") val mongoId: String? = null,
    @SerializedName("report_id") val reportId: String? = null,
    @SerializedName("reportId") val reportIdCamel: String? = null,
    @SerializedName("sender_id") val senderId: String? = null,
    @SerializedName("sender_name") val senderName: String? = null,
    @SerializedName("sender_role") val senderRole: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("text") val text: String? = null,
    @SerializedName("sent_at") val sentAt: String? = null,
    @SerializedName("createdAt") val createdAt: String? = null
) {
    val effectiveId: String
        get() = id?.ifBlank { null }
            ?: mongoId?.ifBlank { null }
            ?: "${effectiveSenderName}_${effectiveSentAt}_${effectiveMessage.take(15)}"

    val effectiveSenderName: String
        get() = senderName?.ifBlank { null } ?: "CEGCT Official"

    val effectiveSenderRole: String
        get() = senderRole?.ifBlank { null } ?: "officer"

    val effectiveMessage: String
        get() = message?.ifBlank { null } ?: text.orEmpty()

    val effectiveSentAt: String
        get() = sentAt?.ifBlank { null } ?: createdAt?.ifBlank { null } ?: "Just now"

    val isFromCitizen: Boolean
        get() = effectiveSenderRole.equals("citizen", ignoreCase = true) ||
                effectiveSenderRole.equals("user", ignoreCase = true) ||
                (NetworkClient.currentUser?.name != null && effectiveSenderName.equals(NetworkClient.currentUser?.name, ignoreCase = true))
}

data class NotificationDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("_id") val mongoId: String? = null,
    @SerializedName("report_id") val reportId: String? = null,
    @SerializedName("reportId") val reportIdCamel: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("body") val body: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("is_read") val isReadSnake: Boolean? = null,
    @SerializedName("isRead") val isReadCamel: Boolean? = null,
    @SerializedName("created_at") val createdAtSnake: String? = null,
    @SerializedName("createdAt") val createdAtCamel: String? = null
) {
    val effectiveId: String
        get() = id?.ifBlank { null } ?: mongoId?.ifBlank { null } ?: "${effectiveReportId}_${effectiveType}_${effectiveCreatedAt}"

    val effectiveReportId: String
        get() = reportId?.ifBlank { null } ?: reportIdCamel?.ifBlank { null } ?: ""

    val effectiveType: String
        get() = type?.uppercase() ?: "REPORT_STATUS"

    val isStatusUpdate: Boolean
        get() = effectiveType.contains("STATUS")

    val isAdminReply: Boolean
        get() = effectiveType.contains("REPLY") || effectiveType.contains("MESSAGE") || effectiveType.contains("CHAT")

    val typeLabel: String
        get() = when {
            isAdminReply -> "New reply on your report"
            isStatusUpdate -> "Report status updated"
            else -> "Notification"
        }

    val effectiveTitle: String
        get() = title?.ifBlank { null } ?: typeLabel

    val effectiveBody: String
        get() = body?.ifBlank { null } ?: message?.ifBlank { null } ?: "Update received for report $effectiveReportId."

    val isRead: Boolean
        get() = isReadSnake == true || isReadCamel == true

    val effectiveCreatedAt: String
        get() = createdAtSnake?.ifBlank { null } ?: createdAtCamel?.ifBlank { null } ?: "Just now"
}

data class SendMessageRequest(
    @SerializedName("message") val message: String
)

data class NetworkWrapper<T>(
    @SerializedName("data") val data: T?,
    @SerializedName("error") val error: String?
)
