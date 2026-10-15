package com.example.cegct.core.firestore.models

/**
 * Represents an inspection record in `reports/{reportId}/inspections` or `inspections`.
 * Conducted by Inspectors or scheduled by Admins.
 */
data class InspectionEntity(
    val inspectionId: String = "",
    val reportId: String = "",
    val inspectorId: String = "",
    val inspectorName: String = "",
    val findings: String = "",
    val recommendations: String = "",
    val status: String = "COMPLETED", // "SCHEDULED", "IN_PROGRESS", "COMPLETED", "CANCELLED"
    val imageUrls: List<String> = emptyList(),
    val scheduledDate: Long? = null,
    val completedAt: Long? = null,
    val createdAt: Long? = null
)
