package com.example.cegct.features.auth

import androidx.compose.runtime.Composable
import com.example.cegct.features.auth.ui.AuthViewModel
import com.example.cegct.features.auth.ui.RegisterScreen

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = AuthViewModel(),
    onRegisterSuccess: () -> Unit,
    onBack: () -> Unit
) {
    RegisterScreen(
        viewModel = viewModel,
        onRegisterSuccess = onRegisterSuccess,
        onBack = onBack
    )
}
