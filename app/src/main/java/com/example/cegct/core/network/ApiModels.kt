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

data class NetworkWrapper<T>(
    @SerializedName("data") val data: T?,
    @SerializedName("error") val error: String?
)
