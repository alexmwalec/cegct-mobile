package com.example.cegct.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cegct.ui.theme.GreenGradientEnd
import com.example.cegct.ui.theme.GreenGradientStart
import com.example.cegct.ui.theme.GreenPrimary

// Material Vector Icons for Profile Settings Cards
private val LanguageIcon: ImageVector = ImageVector.Builder("Language", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(GreenPrimary)) {
        moveTo(11.99f, 2f)
        curveTo(6.47f, 2f, 2f, 6.48f, 2f, 12f)
        curveTo(2f, 17.52f, 6.47f, 22f, 11.99f, 22f)
        curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
        curveTo(22f, 6.48f, 17.52f, 2f, 11.99f, 2f)
        close()
        moveTo(18.92f, 8f)
        lineTo(15.78f, 8f)
        curveTo(15.44f, 6.64f, 14.89f, 5.37f, 14.15f, 4.22f)
        curveTo(16.27f, 4.93f, 17.98f, 6.32f, 18.92f, 8f)
        close()
    }
}.build()

private val LockKeyIcon: ImageVector = ImageVector.Builder("LockKey", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(GreenPrimary)) {
        moveTo(18f, 8f)
        lineTo(17f, 8f)
        lineTo(17f, 6f)
        curveTo(17f, 3.24f, 14.76f, 1f, 12f, 1f)
        curveTo(9.24f, 1f, 7f, 3.24f, 7f, 6f)
        lineTo(7f, 8f)
        lineTo(6f, 8f)
        curveTo(4.9f, 8f, 4f, 8.9f, 4f, 10f)
        lineTo(4f, 20f)
        curveTo(4f, 21.1f, 4.9f, 22f, 6f, 22f)
        lineTo(18f, 22f)
        curveTo(19.1f, 22f, 20f, 21.1f, 20f, 20f)
        lineTo(20f, 10f)
        curveTo(20f, 8.9f, 19.1f, 8f, 18f, 8f)
        close()
        moveTo(12f, 17f)
        curveTo(10.9f, 17f, 10f, 16.1f, 10f, 15f)
        curveTo(10f, 13.9f, 10.9f, 13f, 12f, 13f)
        curveTo(13.1f, 13f, 14f, 13.9f, 14f, 15f)
        curveTo(14f, 16.1f, 13.1f, 17f, 12f, 17f)
        close()
        moveTo(15.1f, 8f)
        lineTo(8.9f, 8f)
        lineTo(8.9f, 6f)
        curveTo(8.9f, 4.29f, 10.29f, 2.9f, 12f, 2.9f)
        curveTo(13.71f, 2.9f, 15.1f, 4.29f, 15.1f, 6f)
        lineTo(15.1f, 8f)
        close()
    }
}.build()

private val DeleteIcon: ImageVector = ImageVector.Builder("Delete", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color(0xFFE53935))) {
        moveTo(6f, 19f)
        curveTo(6f, 20.1f, 6.9f, 21f, 8f, 21f)
        lineTo(16f, 21f)
        curveTo(17.1f, 21f, 18f, 20.1f, 18f, 19f)
        lineTo(18f, 7f)
        lineTo(6f, 7f)
        lineTo(6f, 19f)
        close()
        moveTo(19f, 4f)
        lineTo(15.5f, 4f)
        lineTo(14.5f, 3f)
        lineTo(9.5f, 3f)
        lineTo(8.5f, 4f)
        lineTo(5f, 4f)
        lineTo(5f, 6f)
        lineTo(19f, 6f)
        lineTo(19f, 4f)
        close()
    }
}.build()

private val LogoutIcon: ImageVector = ImageVector.Builder("Logout", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color(0xFFE53935))) {
        moveTo(17f, 7f)
        lineTo(15.59f, 8.41f)
        lineTo(17.17f, 10f)
        lineTo(9f, 10f)
        lineTo(9f, 12f)
        lineTo(17.17f, 12f)
        lineTo(15.59f, 13.58f)
        lineTo(17f, 15f)
        lineTo(21f, 11f)
        close()
        moveTo(4f, 5f)
        lineTo(12f, 5f)
        lineTo(12f, 3f)
        lineTo(4f, 3f)
        curveTo(2.9f, 3f, 2f, 3.9f, 2f, 5f)
        lineTo(2f, 19f)
        curveTo(2f, 20.1f, 2.9f, 21f, 4f, 21f)
        lineTo(12f, 21f)
        lineTo(12f, 19f)
        lineTo(4f, 19f)
        close()
    }
}.build()

private val EditPencilIcon: ImageVector = ImageVector.Builder("EditPencil", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color.White)) {
        moveTo(3f, 17.25f)
        lineTo(3f, 21f)
        lineTo(7.25f, 21f)
        lineTo(17.81f, 10.44f)
        lineTo(13.56f, 6.19f)
        lineTo(3f, 17.25f)
        close()
        moveTo(20.71f, 7.04f)
        curveTo(21.1f, 6.65f, 21.1f, 6.02f, 20.71f, 5.63f)
        lineTo(18.37f, 3.29f)
        curveTo(17.98f, 2.9f, 17.35f, 2.9f, 16.96f, 3.29f)
        lineTo(15.13f, 5.12f)
        lineTo(19.38f, 9.37f)
        lineTo(20.71f, 7.04f)
        close()
    }
}.build()

@Composable
fun ProfileScreen(
    onNavigateToEditProfile: () -> Unit,
    onNavigateToChangePassword: () -> Unit,
    onLogout: () -> Unit,
    onBack: () -> Unit
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Log Out Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text(
                    text = "Log Out",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to log out of your citizen account?",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Log Out")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Delete Account Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "Delete Account",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete your account? All saved reports and data will be permanently removed. This action cannot be undone.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteDialog = false
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete Account")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Screen 4 Header Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(GreenGradientStart, GreenGradientEnd)
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "A",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GreenPrimary
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = "Alex C Mwale",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    text = "Joined Oct 2026",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }
                        }

                        // Edit Profile Circle Card Button
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.25f))
                                .clickable { onNavigateToEditProfile() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = EditPencilIcon,
                                contentDescription = "Edit Profile",
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Application Settings",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 1. Language Card
                Card(
                    onClick = { },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(LanguageIcon, contentDescription = "Language", tint = GreenPrimary, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Language",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                        }
                        Text("›", style = MaterialTheme.typography.titleLarge, color = Color.Gray)
                    }
                }

                // 2. Change Password Card
                Card(
                    onClick = onNavigateToChangePassword,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(LockKeyIcon, contentDescription = "Change Password", tint = GreenPrimary, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Change Password",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                        }
                        Text("›", style = MaterialTheme.typography.titleLarge, color = Color.Gray)
                    }
                }

                // 3. Delete Account Card
                Card(
                    onClick = { showDeleteDialog = true },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(DeleteIcon, contentDescription = "Delete Account", tint = Color(0xFFE53935), modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Delete Account",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFFE53935)
                                )
                            )
                        }
                        Text("›", style = MaterialTheme.typography.titleLarge, color = Color.Gray)
                    }
                }

                // 4. Log Out Card
                Card(
                    onClick = { showLogoutDialog = true },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(LogoutIcon, contentDescription = "Log Out", tint = Color(0xFFE53935), modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Log Out",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFFE53935)
                                )
                            )
                        }
                        Text("›", style = MaterialTheme.typography.titleLarge, color = Color.Gray)
                    }
                }
            }
        }
    }
}
