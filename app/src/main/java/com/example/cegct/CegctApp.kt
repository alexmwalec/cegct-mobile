package com.example.cegct

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import com.example.cegct.features.auth.ForgotPasswordScreen
import com.example.cegct.features.auth.ResetPasswordScreen
import com.example.cegct.features.auth.ui.AuthViewModel
import com.example.cegct.features.auth.ui.LoginScreen
import com.example.cegct.features.auth.ui.RegisterScreen
import com.example.cegct.features.auth.ui.VerifyEmailScreen
import com.example.cegct.features.auth.ui.WelcomeScreen
import com.example.cegct.features.home.HomeScreen
import com.example.cegct.features.map.MapScreen
import com.example.cegct.features.notifications.NotificationsScreen
import com.example.cegct.features.profile.ProfileScreen
import com.example.cegct.features.report.ReportScreen

enum class AuthScreenState {
    WELCOME,
    LOGIN,
    SIGNUP,
    VERIFY_EMAIL,
    FORGOT_PASSWORD,
    RESET_PASSWORD,
    HOME,
    REPORT,
    MAP,
    NOTIFICATIONS,
    PROFILE
}

@Composable
fun CegctApp(
    viewModel: AuthViewModel = remember { AuthViewModel() }
) {
    val backStack = remember { mutableStateListOf(AuthScreenState.WELCOME) }
    val currentScreen = backStack.lastOrNull() ?: AuthScreenState.WELCOME
    var registeredEmail by remember { mutableStateOf("namadinga@example.com") }
    var selectedCategoryForReport by remember { mutableStateOf("Illegal dumping") }

    fun navigateTo(screen: AuthScreenState) {
        if (backStack.lastOrNull() != screen) {
            backStack.add(screen)
        }
    }

    fun navigateBack() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
        }
    }

    // Intercept phone hardware/gesture back button so user navigates back instead of exiting app
    BackHandler(enabled = backStack.size > 1) {
        navigateBack()
    }

    when (currentScreen) {
        AuthScreenState.WELCOME -> {
            WelcomeScreen(
                viewModel = viewModel,
                onNavigateToLogin = {
                    backStack.clear()
                    backStack.add(AuthScreenState.LOGIN)
                }
            )
        }
        AuthScreenState.LOGIN -> {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    backStack.clear()
                    backStack.add(AuthScreenState.HOME)
                },
                onNavigateToForgotPassword = { navigateTo(AuthScreenState.FORGOT_PASSWORD) },
                onNavigateToRegister = { navigateTo(AuthScreenState.SIGNUP) },
                onBack = { navigateBack() }
            )
        }
        AuthScreenState.SIGNUP -> {
            RegisterScreen(
                viewModel = viewModel,
                onRegisterSuccess = {
                    navigateTo(AuthScreenState.VERIFY_EMAIL)
                },
                onNavigateToLogin = { navigateTo(AuthScreenState.LOGIN) },
                onBack = { navigateBack() }
            )
        }
        AuthScreenState.VERIFY_EMAIL -> {
            VerifyEmailScreen(
                emailAddress = registeredEmail,
                viewModel = viewModel,
                onVerifySuccess = {
                    backStack.clear()
                    backStack.add(AuthScreenState.HOME)
                },
                onChangeEmail = { navigateBack() }
            )
        }
        AuthScreenState.FORGOT_PASSWORD -> {
            ForgotPasswordScreen(
                viewModel = viewModel,
                onResetRequested = { navigateTo(AuthScreenState.RESET_PASSWORD) },
                onBack = { navigateBack() }
            )
        }
        AuthScreenState.RESET_PASSWORD -> {
            ResetPasswordScreen(
                viewModel = viewModel,
                onResetSuccess = {
                    backStack.clear()
                    backStack.add(AuthScreenState.LOGIN)
                }
            )
        }
        AuthScreenState.HOME -> {
            HomeScreen(
                onNavigateToReport = { category ->
                    selectedCategoryForReport = category
                    navigateTo(AuthScreenState.REPORT)
                },
                onNavigateToMyReports = { navigateTo(AuthScreenState.NOTIFICATIONS) },
                onNavigateToMap = { navigateTo(AuthScreenState.MAP) },
                onNavigateToProfile = { navigateTo(AuthScreenState.PROFILE) }
            )
        }
        AuthScreenState.REPORT -> {
            ReportScreen(
                category = selectedCategoryForReport,
                onSubmitSuccess = {
                    backStack.clear()
                    backStack.add(AuthScreenState.HOME)
                },
                onBack = { navigateBack() }
            )
        }
        AuthScreenState.MAP -> {
            MapScreen(
                onConfirmLocation = { navigateBack() },
                onBack = { navigateBack() }
            )
        }
        AuthScreenState.NOTIFICATIONS -> {
            NotificationsScreen(
                onBack = { navigateBack() }
            )
        }
        AuthScreenState.PROFILE -> {
            ProfileScreen(
                onLogout = {
                    backStack.clear()
                    backStack.add(AuthScreenState.LOGIN)
                },
                onBack = { navigateBack() }
            )
        }
    }
}
