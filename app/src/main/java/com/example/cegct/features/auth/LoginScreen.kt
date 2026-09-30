package com.example.cegct.features.auth

import androidx.compose.runtime.Composable
import com.example.cegct.features.auth.ui.AuthViewModel
import com.example.cegct.features.auth.ui.LoginScreen

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = AuthViewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onBack: () -> Unit
) {
    LoginScreen(
        viewModel = viewModel,
        onLoginSuccess = onLoginSuccess,
        onNavigateToForgotPassword = onNavigateToForgotPassword,
        onBack = onBack
    )
}
