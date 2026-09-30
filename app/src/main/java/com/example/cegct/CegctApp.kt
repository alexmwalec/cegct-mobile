package com.example.cegct

import androidx.compose.runtime.*
import com.example.cegct.features.auth.ForgotPasswordScreen
import com.example.cegct.features.auth.ui.AuthViewModel
import com.example.cegct.features.auth.ui.LoginScreen
import com.example.cegct.features.auth.ui.RegisterScreen
import com.example.cegct.features.auth.ui.VerifyEmailScreen
import com.example.cegct.features.auth.ui.WelcomeScreen
import com.example.cegct.features.home.HomeScreen

enum class AuthScreenState {
    WELCOME,
    LOGIN,
    SIGNUP,
    VERIFY_EMAIL,
    FORGOT_PASSWORD,
    HOME
}

@Composable
fun CegctApp(
    viewModel: AuthViewModel = remember { AuthViewModel() }
) {
    var currentScreen by remember { mutableStateOf(AuthScreenState.WELCOME) }
    var registeredEmail by remember { mutableStateOf("namadinga@example.com") }

    when (currentScreen) {
        AuthScreenState.WELCOME -> {
            WelcomeScreen(
                viewModel = viewModel,
                onNavigateToLogin = { currentScreen = AuthScreenState.LOGIN },
                onNavigateToRegister = { currentScreen = AuthScreenState.SIGNUP },
                onContinueAnonymously = { currentScreen = AuthScreenState.HOME }
            )
        }
        AuthScreenState.LOGIN -> {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = { currentScreen = AuthScreenState.HOME },
                onNavigateToForgotPassword = { currentScreen = AuthScreenState.FORGOT_PASSWORD },
                onBack = { currentScreen = AuthScreenState.WELCOME }
            )
        }
        AuthScreenState.SIGNUP -> {
            RegisterScreen(
                viewModel = viewModel,
                onRegisterSuccess = {
                    currentScreen = AuthScreenState.VERIFY_EMAIL
                },
                onBack = { currentScreen = AuthScreenState.WELCOME }
            )
        }
        AuthScreenState.VERIFY_EMAIL -> {
            VerifyEmailScreen(
                emailAddress = registeredEmail,
                viewModel = viewModel,
                onVerifySuccess = { currentScreen = AuthScreenState.HOME },
                onChangeEmail = { currentScreen = AuthScreenState.SIGNUP }
            )
        }
        AuthScreenState.FORGOT_PASSWORD -> {
            ForgotPasswordScreen(
                viewModel = viewModel,
                onResetRequested = { currentScreen = AuthScreenState.LOGIN },
                onBack = { currentScreen = AuthScreenState.LOGIN }
            )
        }
        AuthScreenState.HOME -> {
            HomeScreen(
                onNavigateToReport = {},
                onNavigateToMyReports = {},
                onNavigateToMap = {},
                onNavigateToProfile = { currentScreen = AuthScreenState.WELCOME }
            )
        }
    }
}
