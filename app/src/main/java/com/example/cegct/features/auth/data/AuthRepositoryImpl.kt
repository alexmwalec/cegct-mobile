package com.example.cegct.features.auth.data

import com.example.cegct.core.network.LoginRequest
import com.example.cegct.core.network.NetworkClient
import com.example.cegct.core.network.RegisterRequest
import com.example.cegct.features.auth.domain.AuthRepository
import org.json.JSONObject

class AuthRepositoryImpl : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            val response = NetworkClient.apiService.login(
                LoginRequest(email = email.trim(), password = password)
            )
            if (response.isSuccessful && response.body() != null) {
                val authBody = response.body()!!
                NetworkClient.authToken = authBody.token
                NetworkClient.currentUser = authBody.user
                NetworkClient.connectSocket(authBody.token)
                Result.success(true)
            } else {
                val rawError = response.errorBody()?.string() ?: ""
                val errorMessage = parseErrorMessage(rawError)
                    ?: "Invalid email or password (HTTP ${response.code()})."
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(
                Exception("Cannot connect to server. Ensure backend is running on ${NetworkClient.BASE_URL}")
            )
        }
    }

    override suspend fun loginWithGoogle(): Result<Boolean> {
        return Result.failure(
            Exception("Google sign-in is disabled. Please sign up or log in using your account credentials.")
        )
    }

    override suspend fun register(
        fullName: String,
        email: String,
        phone: String,
        password: String
    ): Result<Boolean> {
        return try {
            val response = NetworkClient.apiService.register(
                RegisterRequest(
                    name = fullName.trim(),
                    email = email.trim(),
                    phone = phone.trim(),
                    password = password
                )
            )
            if (response.isSuccessful && response.body() != null) {
                val authBody = response.body()!!
                NetworkClient.authToken = authBody.token
                NetworkClient.currentUser = authBody.user
                NetworkClient.connectSocket(authBody.token)
                Result.success(true)
            } else {
                val rawError = response.errorBody()?.string() ?: ""
                val errorMessage = parseErrorMessage(rawError)
                    ?: "Registration failed (HTTP ${response.code()}). Please check server logs."
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(
                Exception("Cannot connect to server. Ensure backend is running on ${NetworkClient.BASE_URL}")
            )
        }
    }

    override suspend fun verifyEmail(email: String, code: String): Result<Boolean> {
        return Result.success(true)
    }

    override suspend fun resendVerificationCode(email: String): Result<Boolean> {
        return Result.success(true)
    }

    override suspend fun forgotPassword(email: String): Result<Boolean> {
        return Result.success(true)
    }

    override suspend fun resetPassword(token: String, newPassword: String): Result<Boolean> {
        return Result.success(true)
    }

    override suspend fun continueAnonymously(): Result<Boolean> {
        return Result.failure(
            Exception("Guest mode is disabled. Please sign up or log in with your credentials.")
        )
    }

    override suspend fun logout() {
        NetworkClient.authToken = null
        NetworkClient.currentUser = null
        NetworkClient.disconnectSocket()
    }

    private fun parseErrorMessage(json: String): String? {
        return try {
            val trimmed = json.trim()
            if (trimmed.isBlank()) null
            else if (trimmed.startsWith("{")) {
                val obj = JSONObject(trimmed)
                if (obj.has("error")) obj.getString("error")
                else if (obj.has("message")) obj.getString("message")
                else null
            } else {
                trimmed.take(150)
            }
        } catch (_: Exception) {
            json.take(150).ifBlank { null }
        }
    }
}
