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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    fun login(email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.login(email, pass)
            _isLoading.value = false
            result.onSuccess { onSuccess() }.onFailure { _authError.value = it.localizedMessage }
        }
    }

    fun register(email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.register(email, pass)
            _isLoading.value = false
            result.onSuccess { onSuccess() }.onFailure { _authError.value = it.localizedMessage }
        }
    }

    fun verifyEmail(code: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.verifyEmail(code)
            _isLoading.value = false
            result.onSuccess { onSuccess() }.onFailure { _authError.value = it.localizedMessage }
        }
    }

    fun forgotPassword(email: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.forgotPassword(email)
            _isLoading.value = false
            result.onSuccess { onSuccess() }.onFailure { _authError.value = it.localizedMessage }
        }
    }

    fun resetPassword(token: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.resetPassword(token, pass)
            _isLoading.value = false
            result.onSuccess { onSuccess() }.onFailure { _authError.value = it.localizedMessage }
        }
    }
}
