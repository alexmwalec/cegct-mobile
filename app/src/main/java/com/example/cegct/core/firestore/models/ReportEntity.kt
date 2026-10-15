package com.example.cegct.core.firestore.models

/**
 * Represents a case/report document in the `reports` collection.
 * Submitted by mobile app users, reviewed and managed by Web Admins.
 */
data class ReportEntity(
    val reportId: String = "",
    val userId: String = "",
    val userName: String = "",
    val userEmail: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = ReportCategory.OTHER.name,
    val status: String = ReportStatus.SUBMITTED.name,
    val urgency: String = UrgencyLevel.MEDIUM.name,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = "",
    val imageUrls: List<String> = emptyList(),
    val assignedInspectorId: String? = null,
    val assignedInspectorName: String? = null,
    val adminNotes: String? = null,
    val resolutionDetails: String? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val resolvedAt: Long? = null
)

enum class ReportStatus {
    SUBMITTED,
    UNDER_REVIEW,
    INSPECTION_SCHEDULED,
    IN_PROGRESS,
    RESOLVED,
    REJECTED
}

enum class UrgencyLevel {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

enum class ReportCategory {
    ILLEGAL_DUMPING,
    WATER_POLLUTION,
    AIR_POLLUTION,
    NOISE_POLLUTION,
    DEFORESTATION,
    HAZARDOUS_WASTE,
    OTHER
}
