package com.example.cegct.features.profile

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
import com.example.cegct.ui.theme.GreenPrimary

private val EyeIcon: ImageVector = ImageVector.Builder(
    name = "EyePass",
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
    name = "EyeOffPass",
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
fun ChangePasswordScreen(
    onPasswordUpdated: () -> Unit,
    onBack: () -> Unit
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var oldVisible by remember { mutableStateOf(false) }
    var newVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    var isUpdating by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            GreenCurvedHeader(
                title = "Change Password",
                subtitle = "Update your security credentials",
                onBack = onBack
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (errorMessage != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = errorMessage ?: "",
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                // Old Password Field
                OutlinedTextField(
                    value = oldPassword,
                    onValueChange = {
                        oldPassword = it
                        errorMessage = null
                    },
                    label = { Text("Old Password") },
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = if (oldVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { oldVisible = !oldVisible }) {
                            Icon(
                                imageVector = if (oldVisible) EyeIcon else EyeOffIcon,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // New Password Field
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = {
                        newPassword = it
                        errorMessage = null
                    },
                    label = { Text("New Password") },
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = if (newVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { newVisible = !newVisible }) {
                            Icon(
                                imageVector = if (newVisible) EyeIcon else EyeOffIcon,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Confirm Password Field
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        errorMessage = null
                    },
                    label = { Text("Confirm Password") },
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { confirmVisible = !confirmVisible }) {
                            Icon(
                                imageVector = if (confirmVisible) EyeIcon else EyeOffIcon,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Update Password Button
                Button(
                    onClick = {
                        if (oldPassword.isBlank()) {
                            errorMessage = "Enter your current old password"
                        } else if (newPassword.length < 8) {
                            errorMessage = "New password must be at least 8 characters"
                        } else if (newPassword != confirmPassword) {
                            errorMessage = "New password and Confirm password do not match"
                        } else {
                            isUpdating = true
                            onPasswordUpdated()
                        }
                    },
                    enabled = !isUpdating,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (isUpdating) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.5.dp
                        )
                    } else {
                        Text(
                            text = "Update Password",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}
