package com.example.cegct.features.auth.ui

import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader
import com.example.cegct.ui.theme.GreenPrimary

private val EyeIcon: ImageVector = ImageVector.Builder(
    name = "Eye",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = null, stroke = SolidColor(Color.Gray), strokeLineWidth = 2f) {
        moveTo(1f, 12f)
        curveTo(1f, 12f, 5f, 4f, 12f, 4f)
        curveTo(12f, 4f, 19f, 4f, 23f, 12f)
        curveTo(23f, 12f, 19f, 20f, 12f, 20f)
        curveTo(12f, 20f, 5f, 20f, 1f, 12f)
        close()
        moveTo(12f, 15f)
        curveTo(13.6569f, 15f, 15f, 13.6569f, 15f, 12f)
        curveTo(15f, 10.3431f, 13.6569f, 9f, 12f, 9f)
        curveTo(10.3431f, 9f, 9f, 10.3431f, 9f, 12f)
        curveTo(9f, 13.6569f, 10.3431f, 15f, 12f, 15f)
        close()
    }
}.build()

private val EyeOffIcon: ImageVector = ImageVector.Builder(
    name = "EyeOff",
    defaultWidth = 18.dp,
    defaultHeight = 18.dp,
    viewportWidth = 18f,
    viewportHeight = 18f
).apply {
    path(fill = null, stroke = SolidColor(Color.Gray), strokeLineWidth = 2f) {
        moveTo(1f, 1f)
        lineTo(23f, 23f)
        moveTo(10.5f, 10.5f)
        curveTo(9.6f, 11.4f, 9.6f, 12.6f, 10.5f, 13.5f)
        moveTo(1f, 12f)
        curveTo(3f, 8f, 7f, 4f, 12f, 4f)
        curveTo(14.5f, 4f, 16.8f, 5f, 18.6f, 6.4f)
    }
}.build()

private val GoogleIcon: ImageVector = ImageVector.Builder(
    name = "GoogleLogo",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(Color(0xFF4285F4))) {
        moveTo(23.745f, 12.27f)
        curveTo(23.745f, 11.4f, 23.666f, 10.56f, 23.518f, 9.75f)
        lineTo(12f, 9.75f)
        lineTo(12f, 14.51f)
        lineTo(18.583f, 14.51f)
        curveTo(18.3f, 16.03f, 17.443f, 17.31f, 16.155f, 18.17f)
        lineTo(16.155f, 21.22f)
        lineTo(20.088f, 21.22f)
        curveTo(22.389f, 19.1f, 23.745f, 15.98f, 23.745f, 12.27f)
        close()
    }
    path(fill = SolidColor(Color(0xFF34A853))) {
        moveTo(12f, 24f)
        curveTo(15.24f, 24f, 17.958f, 22.925f, 19.95f, 21.083f)
        lineTo(16.155f, 18.17f)
        curveTo(15.105f, 18.875f, 13.77f, 19.3f, 12f, 19.3f)
        curveTo(8.875f, 19.3f, 6.225f, 17.183f, 5.275f, 14.3f)
        lineTo(1.238f, 14.3f)
        lineTo(1.238f, 17.433f)
        curveTo(3.23f, 21.392f, 7.313f, 24f, 12f, 24f)
        close()
    }
    path(fill = SolidColor(Color(0xFFFBBC05))) {
        moveTo(5.275f, 14.3f)
        curveTo(5.03f, 13.567f, 4.892f, 12.792f, 4.892f, 12f)
        curveTo(4.892f, 11.208f, 5.03f, 10.433f, 5.275f, 9.7f)
        lineTo(5.275f, 6.567f)
        lineTo(1.238f, 6.567f)
        curveTo(0.45f, 8.133f, 0f, 9.992f, 0f, 12f)
        curveTo(0f, 14.008f, 0.45f, 15.867f, 1.238f, 17.433f)
        lineTo(5.275f, 14.3f)
        close()
    }
    path(fill = SolidColor(Color(0xFFEA4335))) {
        moveTo(12f, 4.7f)
        curveTo(13.763f, 4.7f, 15.342f, 5.308f, 16.583f, 6.492f)
        lineTo(20.038f, 3.038f)
        curveTo(17.95f, 1.092f, 15.233f, 0f, 12f, 0f)
        curveTo(7.313f, 0f, 3.23f, 2.608f, 1.238f, 6.567f)
        lineTo(5.275f, 9.7f)
        curveTo(6.225f, 6.817f, 8.875f, 4.7f, 12f, 4.7f)
        close()
    }
}.build()

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = AuthViewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onBack: () -> Unit
) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val authError by viewModel.authError.collectAsState()
    val strings = getAuthStrings(selectedLanguage)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Focus / Touch tracking for inline validation
    var emailTouched by remember { mutableStateOf(false) }
    var passwordTouched by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    // Inline validation checks
    val isEmailValid = email.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.isEmpty() || password.length >= 6

    val emailErrorText = if (emailTouched && email.isNotEmpty() && !isEmailValid) strings.errValidEmail else null
    val passwordErrorText = if (passwordTouched && password.isNotEmpty() && !isPasswordValid) strings.errPasswordLength else null

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Green curved header
            GreenCurvedHeader(
                title = strings.loginHeaderTitle,
                onBack = onBack
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),

            ) {
                // Display generic auth error if present
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

                // Email Field with inline validation & KeyboardType.Email
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        if (!emailTouched && it.length > 3) emailTouched = true
                        viewModel.clearError()
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
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            if (!focusState.isFocused && email.isNotEmpty()) {
                                emailTouched = true
                            }
                        }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Password Field with show/hide eye & KeyboardType.Password
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        if (!passwordTouched && it.length > 2) passwordTouched = true
                        viewModel.clearError()
                    },
                    label = { Text(strings.passwordLabel) },
                    isError = passwordErrorText != null,
                    supportingText = {
                        if (passwordErrorText != null) {
                            Text(text = passwordErrorText, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) EyeIcon else EyeOffIcon,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password"

                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            if (email.isNotBlank() && password.isNotBlank()) {
                                viewModel.login(email, password, onLoginSuccess)
                            }
                        }
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            if (!focusState.isFocused && password.isNotEmpty()) {
                                passwordTouched = true
                            }
                        }

                )

                // Forgot Password sits right under the password field
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onNavigateToForgotPassword) {
                        Text(
                            text = strings.forgotPasswordLink,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = GreenPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Main Log In Button with green background and loading state
                Button(
                    onClick = {
                        emailTouched = true
                        passwordTouched = true
                        if (email.isNotBlank() && password.isNotBlank() && isEmailValid) {
                            viewModel.login(email, password, onLoginSuccess)
                        }
                    },
                    enabled = !isLoading && email.isNotBlank() && password.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary,
                        contentColor = Color.White
                    ),
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
                            text = strings.login,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Divider OR
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f))
                    Text(
                        text = "  Or login with  ",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Continue with Google - low-effort option
                OutlinedButton(
                    onClick = {
                        viewModel.loginWithGoogle(onLoginSuccess)
                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = GoogleIcon,
                            contentDescription = "Google Logo",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = strings.continueWithGoogle,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Don't have an account? Sign Up
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account? ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Sign Up",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = GreenPrimary
                        ),
                        modifier = Modifier.clickable { onNavigateToRegister() }
                    )
                }
            }
        }
    }
}
