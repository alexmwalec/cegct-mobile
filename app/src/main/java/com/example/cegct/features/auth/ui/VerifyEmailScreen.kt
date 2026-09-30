package com.example.cegct.features.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader
import com.example.cegct.features.auth.ui.components.OtpInputField
import com.example.cegct.ui.theme.GreenLightButton
import com.example.cegct.ui.theme.GreenPrimary
import kotlinx.coroutines.delay

fun maskEmailAddress(email: String): String {
    if (email.isBlank() || !email.contains("@")) return "n***@example.com"
    val parts = email.split("@")
    val namePart = parts[0]
    val domainPart = parts[1]
    val maskedName = if (namePart.isNotEmpty()) {
        "${namePart.first()}***"
    } else {
        "***"
    }
    return "$maskedName@$domainPart"
}

@Composable
fun VerifyEmailScreen(
    emailAddress: String = "namadinga@example.com",
    viewModel: AuthViewModel = AuthViewModel(),
    onVerifySuccess: () -> Unit,
    onChangeEmail: () -> Unit = {}
) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val authError by viewModel.authError.collectAsState()
    val strings = getAuthStrings(selectedLanguage)

    var otpCode by remember { mutableStateOf("") }
    val maskedEmail = remember(emailAddress) { maskEmailAddress(emailAddress) }

    // 45-second resend timer
    var timerSeconds by remember { mutableIntStateOf(45) }
    var isTimerRunning by remember { mutableStateOf(true) }

    LaunchedEffect(isTimerRunning, timerSeconds) {
        if (isTimerRunning && timerSeconds > 0) {
            delay(1000)
            timerSeconds -= 1
        } else if (timerSeconds == 0) {
            isTimerRunning = false
        }
    }

    val isCodeComplete = otpCode.length == 6

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            GreenCurvedHeader(
                title = strings.verifyHeaderTitle,
                subtitle = null,
                onBack = onChangeEmail
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (authError != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = authError ?: "",
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                // Subtitle: Email address partly masked with "Change email"
                Text(
                    text = strings.verifyHeaderSubtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = maskedEmail,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = onChangeEmail,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "(${strings.changeEmail})",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = GreenPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Six separate OTP boxes with auto-advance and paste support
                OtpInputField(
                    codeLength = 6,
                    otpValue = otpCode,
                    onOtpChanged = { newCode ->
                        otpCode = newCode
                        viewModel.clearError()
                    }
                )

                Spacer(modifier = Modifier.height(28.dp))

                // 45-second resend timer
                if (isTimerRunning && timerSeconds > 0) {
                    Text(
                        text = strings.resendCodeIn.format(timerSeconds),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    TextButton(
                        onClick = {
                            viewModel.resendVerificationCode(emailAddress) {
                                timerSeconds = 45
                                isTimerRunning = true
                            }
                        },
                        enabled = !isLoading
                    ) {
                        Text(
                            text = strings.resendCode,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = GreenPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Verify Button: Stays light green until all 6 digits entered, then turns solid green
                Button(
                    onClick = {
                        if (isCodeComplete) {
                            viewModel.verifyEmail(emailAddress, otpCode, onVerifySuccess)
                        }
                    },
                    enabled = isCodeComplete && !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary,
                        disabledContainerColor = GreenLightButton.copy(alpha = 0.6f),
                        contentColor = Color.White,
                        disabledContentColor = Color.White.copy(alpha = 0.8f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.5.dp
                        )
                    } else {
                        Text(
                            text = strings.verifyButton,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}
