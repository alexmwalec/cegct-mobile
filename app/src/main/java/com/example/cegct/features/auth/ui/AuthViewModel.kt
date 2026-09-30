package com.example.cegct.features.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cegct.features.auth.data.AuthRepositoryImpl
import com.example.cegct.features.auth.domain.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    private val _selectedLanguage = MutableStateFlow(AppLanguage.ENGLISH)
    val selectedLanguage: StateFlow<AppLanguage> = _selectedLanguage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    fun setLanguage(language: AppLanguage) {
        _selectedLanguage.value = language
    }

    fun clearError() {
        _authError.value = null
    }

    fun login(email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null
            val result = authRepository.login(email, pass)
            _isLoading.value = false
            result.onSuccess {
                onSuccess()
            }.onFailure {
                // UX detail: Never reveal whether an email is registered ("Email or password is incorrect")
                _authError.value = getAuthStrings(_selectedLanguage.value).genericAuthError
            }
        }
    }

    fun loginWithGoogle(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null
            val result = authRepository.loginWithGoogle()
            _isLoading.value = false
            result.onSuccess {
                onSuccess()
            }.onFailure {
                _authError.value = getAuthStrings(_selectedLanguage.value).genericAuthError
            }
        }
    }

    fun register(
        fullName: String,
        email: String,
        phone: String,
        pass: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null
            val result = authRepository.register(fullName, email, phone, pass)
            _isLoading.value = false
            result.onSuccess {
                onSuccess()
            }.onFailure {
                _authError.value = it.localizedMessage ?: "Registration failed"
            }
        }
    }

    fun verifyEmail(email: String, code: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null
            val result = authRepository.verifyEmail(email, code)
            _isLoading.value = false
            result.onSuccess {
                onSuccess()
            }.onFailure {
                _authError.value = it.localizedMessage ?: "Verification failed"
            }
        }
    }

    fun resendVerificationCode(email: String, onSent: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.resendVerificationCode(email)
            _isLoading.value = false
            result.onSuccess { onSent() }
        }
    }

    fun forgotPassword(email: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null
            val result = authRepository.forgotPassword(email)
            _isLoading.value = false
            // Always succeed with generic response so registered email status is never leaked
            onSuccess()
        }
    }

    fun resetPassword(token: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null
            val result = authRepository.resetPassword(token, pass)
            _isLoading.value = false
            result.onSuccess { onSuccess() }.onFailure { _authError.value = it.localizedMessage }
        }
    }

    fun continueAnonymously(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null
            val result = authRepository.continueAnonymously()
            _isLoading.value = false
            result.onSuccess { onSuccess() }
        }
    }
}
