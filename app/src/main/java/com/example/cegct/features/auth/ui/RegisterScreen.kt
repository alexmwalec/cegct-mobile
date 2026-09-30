package com.example.cegct.features.auth.ui

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader
import com.example.cegct.features.auth.ui.components.PasswordStrengthMeter
import com.example.cegct.features.auth.ui.components.PrivacyPolicyDialog
import com.example.cegct.ui.theme.GreenPrimary

private val EyeIcon: ImageVector = ImageVector.Builder(
    name = "EyeReg",
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
    name = "EyeOffReg",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
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

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = AuthViewModel(),
    onRegisterSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val authError by viewModel.authError.collectAsState()
    val strings = getAuthStrings(selectedLanguage)

    // 5 Fields
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // Required Terms & Privacy Checkbox
    var termsAccepted by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }

    // Touch / Focus tracking for inline validation
    var nameTouched by remember { mutableStateOf(false) }
    var emailTouched by remember { mutableStateOf(false) }
    var phoneTouched by remember { mutableStateOf(false) }
    var passwordTouched by remember { mutableStateOf(false) }
    var confirmTouched by remember { mutableStateOf(false) }

    // Inline validation checks
    val isNameValid = fullName.isNotBlank()
    val isEmailValid = email.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPhoneValid = phone.isEmpty() || (phone.length >= 7 && phone.all { it.isDigit() || it == '+' || it == ' ' || it == '-' })
    val isPasswordValid = password.isEmpty() || password.length >= 8
    val isConfirmValid = confirmPassword.isEmpty() || password == confirmPassword

    val nameErrorText = if (nameTouched && !isNameValid) strings.errFullNameRequired else null
    val emailErrorText = if (emailTouched && email.isNotEmpty() && !isEmailValid) strings.errValidEmail else null
    val phoneErrorText = if (phoneTouched && phone.isNotEmpty() && !isPhoneValid) strings.errValidPhone else null
    val passwordErrorText = if (passwordTouched && password.isNotEmpty() && !isPasswordValid) strings.errPasswordLength else null
    val confirmErrorText = if (confirmTouched && confirmPassword.isNotEmpty() && !isConfirmValid) strings.errPasswordMismatch else null

    val isFormComplete = fullName.isNotBlank() && email.isNotBlank() && isEmailValid &&
            phone.isNotBlank() && isPhoneValid &&
            password.length >= 8 && password == confirmPassword && termsAccepted

    if (showPrivacyDialog) {
        PrivacyPolicyDialog(
            strings = strings,
            onDismiss = { showPrivacyDialog = false }
        )
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            GreenCurvedHeader(
                title = strings.registerHeaderTitle,
                subtitle = strings.registerHeaderSubtitle,
                onBack = onBack
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
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

                // 1. Full Name Field
                OutlinedTextField(
                    value = fullName,
                    onValueChange = {
                        fullName = it
                        if (!nameTouched) nameTouched = true
                        viewModel.clearError()
                    },
                    label = { Text(strings.fullNameLabel) },
                    isError = nameErrorText != null,
                    supportingText = {
                        if (nameErrorText != null) {
                            Text(text = nameErrorText, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { if (!it.isFocused && fullName.isNotEmpty()) nameTouched = true }
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 2. Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        if (!emailTouched) emailTouched = true
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
                        .onFocusChanged { if (!it.isFocused && email.isNotEmpty()) emailTouched = true }
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 3. Phone Number Field
                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        if (!phoneTouched) phoneTouched = true
                        viewModel.clearError()
                    },
                    label = { Text(strings.phoneNumberLabel) },
                    isError = phoneErrorText != null,
                    supportingText = {
                        if (phoneErrorText != null) {
                            Text(text = phoneErrorText, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { if (!it.isFocused && phone.isNotEmpty()) phoneTouched = true }
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 4. Password Field with Live Strength Meter
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        if (!passwordTouched) passwordTouched = true
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
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { if (!it.isFocused && password.isNotEmpty()) passwordTouched = true }
                )

                // Live Password Strength Meter
                if (password.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    PasswordStrengthMeter(password = password, strings = strings)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 5. Confirm Password Field
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        if (!confirmTouched) confirmTouched = true
                        viewModel.clearError()
                    },
                    label = { Text(strings.confirmPasswordLabel) },
                    isError = confirmErrorText != null,
                    supportingText = {
                        if (confirmErrorText != null) {
                            Text(text = confirmErrorText, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                imageVector = if (confirmPasswordVisible) EyeIcon else EyeOffIcon,
                                contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { if (!it.isFocused && confirmPassword.isNotEmpty()) confirmTouched = true }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Terms and Privacy Checkbox + Reporter Identity Protection Link
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = termsAccepted,
                        onCheckedChange = { termsAccepted = it }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = strings.agreeTermsPrefix,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = strings.termsOfService,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GreenPrimary
                                ),
                                modifier = Modifier.clickable { showPrivacyDialog = true }
                            )
                            Text(
                                text = strings.and,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = strings.privacyPolicy,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GreenPrimary
                                ),
                                modifier = Modifier.clickable { showPrivacyDialog = true }
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "(${strings.identityProtectionNotice})",
                            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                            modifier = Modifier.clickable { showPrivacyDialog = true }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Main Create Account Button
                Button(
                    onClick = {
                        nameTouched = true
                        emailTouched = true
                        phoneTouched = true
                        passwordTouched = true
                        confirmTouched = true
                        if (isFormComplete) {
                            viewModel.register(fullName, email, phone, password, onRegisterSuccess)
                        }
                    },
                    enabled = !isLoading && isFormComplete,
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
                            text = strings.createAccount,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}
