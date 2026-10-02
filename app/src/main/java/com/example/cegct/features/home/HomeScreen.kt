package com.example.cegct.features.home

import androidx.compose.foundation.background
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

// Nav Icons
private val HomeNavIcon: ImageVector = ImageVector.Builder("HomeNav", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(GreenPrimary)) {
        moveTo(10f, 20f)
        lineTo(10f, 14f)
        lineTo(14f, 14f)
        lineTo(14f, 20f)
        lineTo(19f, 20f)
        lineTo(19f, 11f)
        lineTo(22f, 11f)
        lineTo(12f, 3f)
        lineTo(2f, 11f)
        lineTo(5f, 11f)
        lineTo(5f, 20f)
        close()
    }
}.build()

private val PlusNavIcon: ImageVector = ImageVector.Builder("PlusNav", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color.White)) {
        moveTo(19f, 13f)
        lineTo(13f, 13f)
        lineTo(13f, 19f)
        lineTo(11f, 19f)
        lineTo(11f, 13f)
        lineTo(5f, 13f)
        lineTo(5f, 11f)
        lineTo(11f, 11f)
        lineTo(11f, 5f)
        lineTo(13f, 5f)
        lineTo(13f, 11f)
        lineTo(19f, 11f)
        close()
    }
}.build()

private val MapNavIcon: ImageVector = ImageVector.Builder("MapNav", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color.Gray)) {
        moveTo(12f, 2f)
        curveTo(8.13f, 2f, 5f, 5.13f, 5f, 9f)
        curveTo(5f, 14.25f, 12f, 22f, 12f, 22f)
        curveTo(12f, 22f, 19f, 14.25f, 19f, 9f)
        curveTo(19f, 5.13f, 15.87f, 2f, 12f, 2f)
        close()
        moveTo(12f, 11.5f)
        curveTo(10.62f, 11.5f, 9.5f, 10.38f, 9.5f, 9f)
        curveTo(9.5f, 7.62f, 10.62f, 6.5f, 12f, 6.5f)
        curveTo(13.38f, 6.5f, 14.5f, 7.62f, 14.5f, 9f)
        curveTo(14.5f, 10.38f, 13.38f, 11.5f, 12f, 11.5f)
        close()
    }
}.build()

private val ProfileNavIcon: ImageVector = ImageVector.Builder("ProfileNav", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color.Gray)) {
        moveTo(12f, 12f)
        curveTo(14.21f, 12f, 16f, 10.21f, 16f, 8f)
        curveTo(16f, 5.79f, 14.21f, 4f, 12f, 4f)
        curveTo(9.79f, 4f, 8f, 5.79f, 8f, 8f)
        curveTo(8f, 10.21f, 9.79f, 12f, 12f, 12f)
        close()
        moveTo(12f, 14f)
        curveTo(9.33f, 14f, 4f, 15.34f, 4f, 18f)
        lineTo(4f, 20f)
        lineTo(20f, 20f)
        lineTo(20f, 18f)
        curveTo(20f, 15.34f, 14.67f, 14f, 12f, 14f)
        close()
    }
}.build()

@Composable
fun HomeScreen(
    onNavigateToReport: (String) -> Unit,
    onNavigateToMyReports: () -> Unit,
    onNavigateToMap: () -> Unit,
    onNavigateToImpacts: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Scaffold(
        bottomBar = {
            // Navigation Bar: Home, Report (+ sign), Nearby Reports, Profile
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(HomeNavIcon, contentDescription = "Home", tint = GreenPrimary) },
                    label = { Text("Home", color = GreenPrimary, fontWeight = FontWeight.Bold) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigateToReport("Illegal dumping") },
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(GreenPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(PlusNavIcon, contentDescription = "Report", tint = Color.White)
                        }
                    },
                    label = { Text("Report", fontWeight = FontWeight.Bold, color = GreenPrimary) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToMap,
                    icon = { Icon(MapNavIcon, contentDescription = "Nearby Reports") },
                    label = { Text("Nearby Reports") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToProfile,
                    icon = { Icon(ProfileNavIcon, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Top Green Curved Header Banner
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
                            Text("🌍", style = MaterialTheme.typography.titleLarge)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "CEGCT Mobile",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                                color = Color.White
                            )
                        }

                        // Notification Bell Icon
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🔔", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Empowering you to heal the Planet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Good night, Alex Mwale 👋",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "What would you like to do?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // 2x2 Grid Cards: My Reports, Nearby Reports, Impacts, Community
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // 1. My Reports
                        Card(
                            onClick = onNavigateToMyReports,
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(130.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFE8EAF6)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("📋", style = MaterialTheme.typography.bodyLarge)
                                }

                                Column {
                                    Text(
                                        text = "My Reports",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = "Track report status",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }

                        // 2. Nearby Reports (renamed from Map)
                        Card(
                            onClick = onNavigateToMap,
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(130.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFE0F2F1)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("🗺️", style = MaterialTheme.typography.bodyLarge)
                                }

                                Column {
                                    Text(
                                        text = "Nearby Reports",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = "Heatmap & reports",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // 3. Impacts
                        Card(
                            onClick = onNavigateToImpacts,
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(130.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFFFF3E0)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("📊", style = MaterialTheme.typography.bodyLarge)
                                }

                                Column {
                                    Text(
                                        text = "Impacts",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = "Know your contributions",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }

                        // 4. Community
                        Card(
                            onClick = { },
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(130.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFE3F2FD)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("👥", style = MaterialTheme.typography.bodyLarge)
                                }

                                Column {
                                    Text(
                                        text = "Community",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = "Connect & invite",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Bottom Card: Offline Evidence Vault
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFFF8E1)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("📁", style = MaterialTheme.typography.titleMedium)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = "📁 Offline Evidence Vault",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "View saved reports waiting to sync",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                            }
                        }

                        Text("🔄", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}
