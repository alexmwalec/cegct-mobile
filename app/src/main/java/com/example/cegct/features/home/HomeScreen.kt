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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cegct.ui.theme.GreenGradientEnd
import com.example.cegct.ui.theme.GreenGradientStart
import com.example.cegct.ui.theme.GreenPrimary

// Material Vector Icons for Cards
private val AssignmentIcon: ImageVector = ImageVector.Builder("Assignment", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(GreenPrimary)) {
        moveTo(19f, 3f)
        lineTo(14.82f, 3f)
        curveTo(14.4f, 1.84f, 13.3f, 1f, 12f, 1f)
        curveTo(10.7f, 1f, 9.6f, 1.84f, 9.18f, 3f)
        lineTo(5f, 3f)
        curveTo(3.9f, 3f, 3f, 3.9f, 3f, 5f)
        lineTo(3f, 19f)
        curveTo(3f, 20.1f, 3.9f, 21f, 5f, 21f)
        lineTo(19f, 21f)
        curveTo(20.1f, 21f, 21f, 20.1f, 21f, 19f)
        lineTo(21f, 5f)
        curveTo(21f, 3.9f, 20.1f, 3f, 19f, 3f)
        close()
        moveTo(12f, 3f)
        curveTo(12.55f, 3f, 13f, 3.45f, 13f, 4f)
        curveTo(13f, 4.55f, 12.55f, 5f, 12f, 5f)
        curveTo(11.45f, 5f, 11f, 4.55f, 11f, 4f)
        curveTo(11f, 3.45f, 11.45f, 3f, 12f, 3f)
        close()
        moveTo(14f, 17f)
        lineTo(7f, 17f)
        lineTo(7f, 15f)
        lineTo(14f, 15f)
        close()
        moveTo(17f, 13f)
        lineTo(7f, 13f)
        lineTo(7f, 11f)
        lineTo(17f, 11f)
        close()
    }
}.build()

private val MapLocationIcon: ImageVector = ImageVector.Builder("MapLocation", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color(0xFF0288D1))) {
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

private val BarChartIcon: ImageVector = ImageVector.Builder("BarChart", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color(0xFF00796B))) {
        moveTo(5f, 9.2f)
        lineTo(9f, 9.2f)
        lineTo(9f, 19f)
        lineTo(5f, 19f)
        close()
        moveTo(11f, 5f)
        lineTo(15f, 5f)
        lineTo(15f, 19f)
        lineTo(11f, 19f)
        close()
        moveTo(17f, 13f)
        lineTo(21f, 13f)
        lineTo(21f, 19f)
        lineTo(17f, 19f)
        close()
    }
}.build()

private val PeopleGroupIcon: ImageVector = ImageVector.Builder("PeopleGroup", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color(0xFFE65100))) {
        moveTo(16f, 11f)
        curveTo(17.66f, 11f, 18.99f, 9.66f, 18.99f, 8f)
        curveTo(18.99f, 6.34f, 17.66f, 5f, 16f, 5f)
        curveTo(14.34f, 5f, 13f, 6.34f, 13f, 8f)
        curveTo(13f, 9.66f, 14.34f, 11f, 16f, 11f)
        close()
        moveTo(8f, 11f)
        curveTo(9.66f, 11f, 10.99f, 9.66f, 10.99f, 8f)
        curveTo(10.99f, 6.34f, 9.66f, 5f, 8f, 5f)
        curveTo(5f, 9.66f, 6.34f, 11f, 8f, 11f)
        close()
        moveTo(8f, 13f)
        curveTo(5.33f, 13f, 0f, 14.34f, 0f, 17f)
        lineTo(0f, 19f)
        lineTo(16f, 19f)
        lineTo(16f, 17f)
        curveTo(16f, 14.34f, 10.67f, 13f, 8f, 13f)
        close()
    }
}.build()

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

private val DarkGreenHeader = Color(0xFF185835)

private val NotificationIcon: ImageVector = ImageVector.Builder(
    name = "Notifications",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(Color(0xFF1A1A1A))) {
        moveTo(12f, 22f)
        curveTo(13.1f, 22f, 14f, 21.1f, 14f, 20f)
        lineTo(10f, 20f)
        curveTo(10f, 21.1f, 10.89f, 22f, 12f, 22f)
        close()
        moveTo(18f, 16f)
        lineTo(18f, 11f)
        curveTo(18f, 7.93f, 16.36f, 5.36f, 13.5f, 4.68f)
        lineTo(13.5f, 4f)
        curveTo(13.5f, 3.17f, 12.83f, 2.5f, 12f, 2.5f)
        curveTo(11.17f, 2.5f, 10.5f, 3.17f, 10.5f, 4f)
        lineTo(10.5f, 4.68f)
        curveTo(7.63f, 5.36f, 6f, 7.92f, 6f, 11f)
        lineTo(6f, 16f)
        lineTo(4f, 18f)
        lineTo(4f, 19f)
        lineTo(20f, 19f)
        lineTo(20f, 18f)
        lineTo(18f, 16f)
        close()
        moveTo(16f, 17f)
        lineTo(8f, 17f)
        lineTo(8f, 11f)
        curveTo(8f, 8.52f, 9.51f, 6.5f, 12f, 6.5f)
        curveTo(14.49f, 6.5f, 16f, 8.52f, 16f, 11f)
        lineTo(16f, 17f)
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
            // Navigation Bar: Home, Report, Profile
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(HomeNavIcon, contentDescription = "Home", tint = DarkGreenHeader) },
                    label = { Text("Home", color = DarkGreenHeader, fontWeight = FontWeight.Bold) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigateToReport("Illegal dumping") },
                    icon = { Icon(PlusNavIcon, contentDescription = "Report", tint = Color.Gray) },
                    label = { Text("Report", color = Color.Gray) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToProfile,
                    icon = { Icon(ProfileNavIcon, contentDescription = "Profile", tint = Color.Gray) },
                    label = { Text("Profile", color = Color.Gray) }
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
            Surface(
                color = DarkGreenHeader,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🌍", style = MaterialTheme.typography.titleLarge)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "CEGCT",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                                color = Color.White
                            )
                        }

                        // Notification Bell Icon (matching Report page)
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF8BAA9B)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = NotificationIcon,
                                contentDescription = "Notifications",
                                tint = Color(0xFF1A1A1A),
                                modifier = Modifier.size(24.dp)
                            )
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

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding   (horizontal = 20.dp)
            ) {
                Text(
                    text = "Quick Action",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,

                    )
                )

                Spacer(modifier = Modifier.height(40.dp))

                // 2x2 Grid Cards with Material Vector Icons
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
                                        .background(Color(0xFFE8F5E9)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = AssignmentIcon,
                                        contentDescription = "My Reports",
                                        tint = GreenPrimary,
                                        modifier = Modifier.size(24.dp)
                                    )
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

                        // 2. Nearby Reports
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
                                    Icon(
                                        imageVector = MapLocationIcon,
                                        contentDescription = "Nearby Reports",
                                        tint = Color(0xFF0288D1),
                                        modifier = Modifier.size(24.dp)
                                    )
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
                                        .background(Color(0xFFE0F2F1)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = BarChartIcon,
                                        contentDescription = "Impacts",
                                        tint = Color(0xFF00796B),
                                        modifier = Modifier.size(24.dp)
                                    )
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
                                        .background(Color(0xFFFFF3E0)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = PeopleGroupIcon,
                                        contentDescription = "Community",
                                        tint = Color(0xFFE65100),
                                        modifier = Modifier.size(24.dp)
                                    )
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

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
