package com.example.cegct.core.firestore.models

/**
 * Represents a user document in the `users` collection.
 * Roles: "USER", "ADMIN", "INSPECTOR"
 */
data class UserEntity(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val role: String = UserRole.USER.name, // "USER", "ADMIN", "INSPECTOR"
    val fcmToken: String? = null,
    val profileImageUrl: String? = null,
    val isActive: Boolean = true,
    val createdAt: Long? = null,
    val updatedAt: Long? = null
)

enum class UserRole {
    USER,
    ADMIN,
    INSPECTOR
}
