package com.example.cegct.features.auth.domain

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Boolean>
    suspend fun loginWithGoogle(): Result<Boolean>
    suspend fun register(fullName: String, email: String, phone: String, password: String): Result<Boolean>
    suspend fun verifyEmail(email: String, code: String): Result<Boolean>
    suspend fun resendVerificationCode(email: String): Result<Boolean>
    suspend fun forgotPassword(email: String): Result<Boolean>
    suspend fun resetPassword(token: String, newPassword: String): Result<Boolean>
    suspend fun continueAnonymously(): Result<Boolean>
    suspend fun logout()
}
