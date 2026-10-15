package com.example.cegct.core.firestore.models

/**
 * Represents broadcast announcements published by Web Admins in `announcements` collection.
 */
data class AnnouncementEntity(
    val announcementId: String = "",
    val title: String = "",
    val content: String = "",
    val category: String = "GENERAL", // "URGENT", "GENERAL", "COMMUNITY", "POLICY"
    val authorName: String = "Admin",
    val imageUrl: String? = null,
    val isImportant: Boolean = false,
    val createdAt: Long? = null
)
