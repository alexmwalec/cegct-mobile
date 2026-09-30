package com.example.cegct.features.auth

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.cegct.features.auth.ui.AuthViewModel
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader
import com.example.cegct.features.auth.ui.getAuthStrings

@Composable
fun ForgotPasswordScreen(
    viewModel: AuthViewModel = AuthViewModel(),
    onResetRequested: () -> Unit,
    onBack: () -> Unit
) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val strings = getAuthStrings(selectedLanguage)

    var email by remember { mutableStateOf("") }
    var emailTouched by remember { mutableStateOf(false) }
    var isSuccessMessageShown by remember { mutableStateOf(false) }

    val isEmailValid = email.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val emailErrorText = if (emailTouched && email.isNotEmpty() && !isEmailValid) strings.errValidEmail else null

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            GreenCurvedHeader(
                title = strings.forgotPasswordLink.replace("?", ""),
                subtitle = "Enter your registered email to reset your password",
                onBack = onBack
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isSuccessMessageShown) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = "If an account exists for $email, a password reset link has been sent. Please check your inbox.",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailTouched = true
                        isSuccessMessageShown = false
                    },
                    label = { Text(strings.emailLabel) },
                    isError = emailErrorText != null,
                    supportingText = {
                        if (emailErrorText != null) {
                            Text(text = emailErrorText, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        emailTouched = true
                        if (email.isNotBlank() && isEmailValid) {
                            viewModel.forgotPassword(email) {
                                isSuccessMessageShown = true
                                onResetRequested()
                            }
                        }
                    },
                    enabled = !isLoading && email.isNotBlank() && isEmailValid,
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
                            text = "Send Reset Link",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}
