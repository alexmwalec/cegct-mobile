package com.example.cegct.features.auth.data

import com.example.cegct.features.auth.domain.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(email: String, password: String): Result<Boolean> = Result.success(true)
    override suspend fun register(email: String, password: String): Result<Boolean> = Result.success(true)
    override suspend fun verifyEmail(code: String): Result<Boolean> = Result.success(true)
    override suspend fun forgotPassword(email: String): Result<Boolean> = Result.success(true)
    override suspend fun resetPassword(token: String, newPassword: String): Result<Boolean> = Result.success(true)
    override suspend fun continueAnonymously(): Result<Boolean> = Result.success(true)
    override suspend fun logout() {}
}
