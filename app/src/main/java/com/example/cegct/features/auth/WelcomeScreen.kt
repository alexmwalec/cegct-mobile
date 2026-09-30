package com.example.cegct.features.auth

import androidx.compose.runtime.Composable
import com.example.cegct.features.auth.ui.AuthViewModel
import com.example.cegct.features.auth.ui.WelcomeScreen

@Composable
fun WelcomeScreen(
    viewModel: AuthViewModel = AuthViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onContinueAnonymously: () -> Unit
) {
    WelcomeScreen(
        viewModel = viewModel,
        onNavigateToLogin = onNavigateToLogin,
        onNavigateToRegister = onNavigateToRegister,
        onContinueAnonymously = onContinueAnonymously
    )
}
