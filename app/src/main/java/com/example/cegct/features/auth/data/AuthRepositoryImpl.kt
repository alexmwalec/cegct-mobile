package com.example.cegct.features.auth.data

import com.example.cegct.features.auth.domain.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(email: String, password: String): Result<Boolean> {
        delay(800) // Simulate network delay for pilot area connectivity
        return Result.success(true)
    }

    override suspend fun loginWithGoogle(): Result<Boolean> {
        delay(800)
        return Result.success(true)
    }

    override suspend fun register(
        fullName: String,
        email: String,
        phone: String,
        password: String
    ): Result<Boolean> {
        delay(1000)
        return Result.success(true)
    }

    override suspend fun verifyEmail(email: String, code: String): Result<Boolean> {
        delay(800)
        return Result.success(true)
    }

    override suspend fun resendVerificationCode(email: String): Result<Boolean> {
        delay(600)
        return Result.success(true)
    }

    override suspend fun forgotPassword(email: String): Result<Boolean> {
        delay(800)
        return Result.success(true)
    }

    override suspend fun resetPassword(token: String, newPassword: String): Result<Boolean> {
        delay(800)
        return Result.success(true)
    }

    override suspend fun continueAnonymously(): Result<Boolean> {
        delay(400)
        return Result.success(true)
    }

    override suspend fun logout() {}
}
