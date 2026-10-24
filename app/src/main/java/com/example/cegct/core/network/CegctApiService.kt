package com.example.cegct.core.network

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface CegctApiService {

    @GET("health")
    suspend fun getHealth(): Response<Map<String, Any>>

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<AuthResponse>

    @POST("auth/mobile/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthResponse>

    @GET("auth/me")
    suspend fun getMe(
        @Header("Authorization") token: String
    ): Response<NetworkWrapper<UserDto>>

    @Multipart
    @POST("reports")
    suspend fun createReport(
        @Header("Authorization") token: String,
        @Part parts: List<MultipartBody.Part>
    ): Response<NetworkWrapper<ReportResponseDto>>

    @GET("reports")
    suspend fun getReports(
        @Header("Authorization") token: String
    ): Response<NetworkWrapper<List<ReportResponseDto>>>

    @GET("reports/{id}")
    suspend fun getReportById(
        @Header("Authorization") token: String,
        @Path("id") reportId: String
    ): Response<NetworkWrapper<ReportResponseDto>>

    @GET("reports/{reportId}/messages")
    suspend fun getReportMessages(
        @Header("Authorization") token: String,
        @Path("reportId") reportId: String
    ): Response<NetworkWrapper<List<ChatMessageDto>>>

    @POST("reports/{reportId}/messages")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Path("reportId") reportId: String,
        @Body request: SendMessageRequest
    ): Response<NetworkWrapper<ChatMessageDto>>

    @GET("notifications")
    suspend fun getNotifications(
        @Header("Authorization") token: String
    ): Response<NetworkWrapper<List<NotificationDto>>>

    @PATCH("notifications/{id}/read")
    suspend fun markNotificationRead(
        @Header("Authorization") token: String,
        @Path("id") notificationId: String
    ): Response<NetworkWrapper<Map<String, Any>>>
}
