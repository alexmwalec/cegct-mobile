package com.example.cegct.features.myreports

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader

private val DarkGreenHeader = Color(0xFF185835)
private val ScreenBackground = Color(0xFFE5F0EE)

private val AssignmentIcon: ImageVector = ImageVector.Builder(
    name = "Assignment",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(Color.White)) {
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

private val AssignmentDarkIcon: ImageVector = ImageVector.Builder(
    name = "AssignmentDark",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(DarkGreenHeader)) {
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

@Composable
fun MyReportsScreen(
    onSelectReport: (String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    var reportsCount by remember { mutableIntStateOf(0) }

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
            // Header with Dark Green color and properties
            GreenCurvedHeader(
                title = "My Reports",
                subtitle = "Track status and response updates in real-time",
                showNotificationBell = true,
                showBackArrow = true,
                onBack = onBack
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Reports Submitted Banner Card in Dark Green
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkGreenHeader),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    modifier = Modifier.fillMaxWidth()
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
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = AssignmentIcon,
                                contentDescription = "Reports Submitted",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = "$reportsCount Reports Submitted",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                ),
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Track status and response updates in real-time.",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                // Empty State Section (No Refresh List Button)
                if (reportsCount == 0) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 32.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(84.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = AssignmentDarkIcon,
                                contentDescription = "No Reports",
                                tint = DarkGreenHeader,
                                modifier = Modifier.size(42.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "No Reports Found",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            color = Color(0xFF1A1A1A)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Environmental issues you submit will appear here.",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                            color = Color(0xFF666666),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    // Sample Submitted Report Item when count > 0
                    Card(
                        onClick = { onSelectReport("#CEGCT-2024-0042") },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Water Contamination Issue",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color(0xFFE0F7FA)
                                ) {
                                    Text(
                                        text = "Submitted",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = Color(0xFF00838F),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "#CEGCT-2024-0042",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Unusual discoloration in the borehole water supply – yellowish tint with foul smell.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "View details →",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = DarkGreenHeader),
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }
    }
}
