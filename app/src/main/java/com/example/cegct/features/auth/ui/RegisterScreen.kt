package com.example.cegct.features.auth.ui

import android.util.Patterns
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.sp
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader
import com.example.cegct.features.auth.ui.components.PasswordStrengthMeter
import com.example.cegct.features.auth.ui.components.PrivacyPolicyDialog

private val DarkGreenHeader = Color(0xFF185835)
private val ScreenBackground = Color(0xFFE5F0EE)
private val FieldBackground = Color(0xFFF2F2F2)
private val LabelTextColor = Color(0xFF1A1A1A)

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

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = AuthViewModel(),
    onRegisterSuccess: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val authError by viewModel.authError.collectAsState()
    val strings = getAuthStrings(selectedLanguage)

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var termsAccepted by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }

    var nameTouched by remember { mutableStateOf(false) }
    var emailTouched by remember { mutableStateOf(false) }
    var phoneTouched by remember { mutableStateOf(false) }
    var passwordTouched by remember { mutableStateOf(false) }
    var confirmTouched by remember { mutableStateOf(false) }

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            GreenCurvedHeader(
                title = strings.registerHeaderTitle,
                subtitle = "Empowering you to heal the Planet",
                showNotificationBell = false,
                showBackArrow = false
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
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

                    // 1. Full Name
                    Text(
                        text = strings.fullNameLabel,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                        color = LabelTextColor
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    TextField(
                        value = fullName,
                        onValueChange = {
                            fullName = it
                            if (!nameTouched) nameTouched = true
                            viewModel.clearError()
                        },
                        placeholder = { Text("Alex C Mwale", color = Color(0xFF888888)) },
                        isError = nameErrorText != null,
                        supportingText = {
                            if (nameErrorText != null) {
                                Text(text = nameErrorText, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = FieldBackground,
                            unfocusedContainerColor = FieldBackground,
                            disabledContainerColor = FieldBackground,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedTextColor = LabelTextColor,
                            unfocusedTextColor = LabelTextColor
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .onFocusChanged { if (!it.isFocused && fullName.isNotEmpty()) nameTouched = true }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 2. Email Address
                    Text(
                        text = strings.emailLabel,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                        color = LabelTextColor
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    TextField(
                        value = email,
                        onValueChange = {
                            email = it
                            if (!emailTouched) emailTouched = true
                            viewModel.clearError()
                        },
                        placeholder = { Text("example@gmail.com", color = Color(0xFF888888)) },
                        isError = emailErrorText != null,
                        supportingText = {
                            if (emailErrorText != null) {
                                Text(text = emailErrorText, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = FieldBackground,
                            unfocusedContainerColor = FieldBackground,
                            disabledContainerColor = FieldBackground,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedTextColor = LabelTextColor,
                            unfocusedTextColor = LabelTextColor
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .onFocusChanged { if (!it.isFocused && email.isNotEmpty()) emailTouched = true }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 3. Phone Number
                    Text(
                        text = strings.phoneNumberLabel,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                        color = LabelTextColor
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    TextField(
                        value = phone,
                        onValueChange = {
                            phone = it
                            if (!phoneTouched) phoneTouched = true
                            viewModel.clearError()
                        },
                        placeholder = { Text("0991234567", color = Color(0xFF888888)) },
                        isError = phoneErrorText != null,
                        supportingText = {
                            if (phoneErrorText != null) {
                                Text(text = phoneErrorText, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = FieldBackground,
                            unfocusedContainerColor = FieldBackground,
                            disabledContainerColor = FieldBackground,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedTextColor = LabelTextColor,
                            unfocusedTextColor = LabelTextColor
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .onFocusChanged { if (!it.isFocused && phone.isNotEmpty()) phoneTouched = true }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 4. Password
                    Text(
                        text = strings.passwordLabel,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                        color = LabelTextColor
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    TextField(
                        value = password,
                        onValueChange = {
                            password = it
                            if (!passwordTouched) passwordTouched = true
                            viewModel.clearError()
                        },
                        placeholder = { Text("••••••••", color = Color(0xFF888888)) },
                        isError = passwordErrorText != null,
                        supportingText = {
                            if (passwordErrorText != null) {
                                Text(text = passwordErrorText, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = FieldBackground,
                            unfocusedContainerColor = FieldBackground,
                            disabledContainerColor = FieldBackground,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedTextColor = LabelTextColor,
                            unfocusedTextColor = LabelTextColor
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) EyeIcon else EyeOffIcon,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .onFocusChanged { if (!it.isFocused && password.isNotEmpty()) passwordTouched = true }
                    )

                    if (password.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        PasswordStrengthMeter(password = password, strings = strings)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 5. Confirm Password
                    Text(
                        text = strings.confirmPasswordLabel,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                        color = LabelTextColor
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    TextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            if (!confirmTouched) confirmTouched = true
                            viewModel.clearError()
                        },
                        placeholder = { Text("••••••••", color = Color(0xFF888888)) },
                        isError = confirmErrorText != null,
                        supportingText = {
                            if (confirmErrorText != null) {
                                Text(text = confirmErrorText, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = FieldBackground,
                            unfocusedContainerColor = FieldBackground,
                            disabledContainerColor = FieldBackground,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedTextColor = LabelTextColor,
                            unfocusedTextColor = LabelTextColor
                        ),
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisible) EyeIcon else EyeOffIcon,
                                    contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .onFocusChanged { if (!it.isFocused && confirmPassword.isNotEmpty()) confirmTouched = true }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Terms and Privacy Checkbox
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = termsAccepted,
                            onCheckedChange = { termsAccepted = it },
                            colors = CheckboxDefaults.colors(checkedColor = DarkGreenHeader),
                            modifier = Modifier.offset(x = (-12).dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.offset(x = (-12).dp)
                        ) {
                            Text(text = strings.agreeTermsPrefix, style = MaterialTheme.typography.bodySmall)
                            Text(
                                text = strings.termsOfService,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = DarkGreenHeader),
                                modifier = Modifier.clickable { showPrivacyDialog = true }
                            )
                            Text(text = strings.and, style = MaterialTheme.typography.bodySmall)
                            Text(
                                text = strings.privacyPolicy,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = DarkGreenHeader),
                                modifier = Modifier.clickable { showPrivacyDialog = true }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Submit Button
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
                            .height(54.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkGreenHeader,
                            contentColor = Color.White,
                            disabledContainerColor = DarkGreenHeader.copy(alpha = 0.6f),
                            disabledContentColor = Color.White.copy(alpha = 0.8f)
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
                                text = strings.createAccount,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Already have an account? ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Log in",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = DarkGreenHeader
                            ),
                            modifier = Modifier.clickable { onNavigateToLogin() }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
