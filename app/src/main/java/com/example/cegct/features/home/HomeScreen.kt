package com.example.cegct.features.home

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

data class IssueCategory(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color
)

private val TrashIcon: ImageVector = ImageVector.Builder("Trash", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color(0xFF2E7D32))) {
        moveTo(6f, 19f)
        curveTo(6f, 20.1f, 6.9f, 21f, 8f, 21f)
        lineTo(16f, 21f)
        curveTo(17.1f, 21f, 18f, 20.1f, 18f, 19f)
        lineTo(18f, 7f)
        lineTo(6f, 7f)
        close()
        moveTo(19f, 4f)
        lineTo(15.5f, 4f)
        lineTo(14.5f, 3f)
        lineTo(9.5f, 3f)
        lineTo(8.5f, 4f)
        lineTo(5f, 4f)
        lineTo(5f, 6f)
        lineTo(19f, 6f)
        close()
    }
}.build()

private val AirPollutionIcon: ImageVector = ImageVector.Builder("Air", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color(0xFF0288D1))) {
        moveTo(19.35f, 10.04f)
        curveTo(18.67f, 6.59f, 15.64f, 4f, 12f, 4f)
        curveTo(9.11f, 4f, 6.6f, 5.64f, 5.35f, 8.04f)
        curveTo(2.34f, 8.36f, 0f, 10.91f, 0f, 14f)
        curveTo(0f, 17.31f, 2.69f, 20f, 6f, 20f)
        lineTo(19f, 20f)
        curveTo(21.76f, 20f, 24f, 17.76f, 24f, 15f)
        curveTo(24f, 12.36f, 21.95f, 10.22f, 19.35f, 10.04f)
        close()
    }
}.build()

private val WarningIcon: ImageVector = ImageVector.Builder("Warning", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color(0xFFE65100))) {
        moveTo(1f, 21f)
        lineTo(23f, 21f)
        lineTo(12f, 2f)
        close()
        moveTo(13f, 18f)
        lineTo(11f, 18f)
        lineTo(11f, 16f)
        lineTo(13f, 16f)
        close()
        moveTo(13f, 14f)
        lineTo(11f, 14f)
        lineTo(11f, 10f)
        lineTo(13f, 10f)
        close()
    }
}.build()

private val WaterIcon: ImageVector = ImageVector.Builder("Water", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color(0xFF0097A7))) {
        moveTo(12f, 2.69f)
        lineTo(5.61f, 9.08f)
        curveTo(2.1f, 12.59f, 2.1f, 18.28f, 5.61f, 21.79f)
        curveTo(9.12f, 25.3f, 14.81f, 25.3f, 18.32f, 21.79f)
        curveTo(21.83f, 18.28f, 21.83f, 12.59f, 18.32f, 9.08f)
        close()
    }
}.build()

private val OtherIcon: ImageVector = ImageVector.Builder("Other", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color(0xFF7B1FA2))) {
        moveTo(12f, 2f)
        curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
        curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
        curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
        close()
        moveTo(11f, 7f)
        lineTo(13f, 7f)
        lineTo(13f, 13f)
        lineTo(11f, 13f)
        close()
        moveTo(11f, 15f)
        lineTo(13f, 15f)
        lineTo(13f, 17f)
        lineTo(11f, 17f)
        close()
    }
}.build()

// Bottom Bar Icons
private val HomeIcon: ImageVector = ImageVector.Builder("HomeNav", 24.dp, 24.dp, 24f, 24f).apply {
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

private val NotifNavIcon: ImageVector = ImageVector.Builder("NotifNav", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color.Gray)) {
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
    onNavigateToProfile: () -> Unit
) {
    val categories = remember {
        listOf(
            IssueCategory("Illegal dumping", "Report unauthorized rubbish & waste piles", TrashIcon, Color(0xFFE8F5E9)),
            IssueCategory("Air pollution", "Report smoke, industrial fumes & emissions", AirPollutionIcon, Color(0xFFE1F5FE)),
            IssueCategory("Unsafe waste disposal", "Report toxic or hazardous chemical dumping", WarningIcon, Color(0xFFFFF3E0)),
            IssueCategory("Water pollution", "Report contaminated rivers, pipes & drainage", WaterIcon, Color(0xFFE0F7FA)),
            IssueCategory("Other Issues", "Report public infrastructure or other concerns", OtherIcon, Color(0xFFF3E5F5))
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(HomeIcon, contentDescription = "Home", tint = GreenPrimary) },
                    label = { Text("Home", color = GreenPrimary, fontWeight = FontWeight.Bold) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToMap,
                    icon = { Icon(MapNavIcon, contentDescription = "Map") },
                    label = { Text("Map") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToMyReports,
                    icon = { Icon(NotifNavIcon, contentDescription = "Notifications") },
                    label = { Text("Notifications") }
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
            // Header Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(GreenGradientStart, GreenGradientEnd)
                        )
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "CEGCT Dashboard",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                            Text(
                                text = "Welcome, Citizen",
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("NZ", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Report civic and environmental issues in your community quickly & anonymously.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            // Category Selection Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Select Issue Type to Report",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Choose a category below to capture photo and details",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Modern Category List Cards
                categories.forEach { category ->
                    Card(
                        onClick = { onNavigateToReport(category.title) },
                        colors = CardDefaults.cardColors(containerColor = category.color),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = category.icon,
                                    contentDescription = category.title,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = category.title,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = category.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
