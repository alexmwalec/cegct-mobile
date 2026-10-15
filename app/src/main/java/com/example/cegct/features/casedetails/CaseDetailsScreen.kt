package com.example.cegct.features.casedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.unit.sp
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader

private val DarkGreenHeader = Color(0xFF185835)
private val ScreenBg = Color(0xFFE5F0EE)

private val ChatIcon: ImageVector = ImageVector.Builder(
    name = "Chat",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(Color.White)) {
        moveTo(20f, 2f)
        lineTo(4f, 2f)
        curveTo(2.9f, 2f, 2f, 2.9f, 2f, 4f)
        lineTo(2f, 22f)
        lineTo(6f, 18f)
        lineTo(20f, 18f)
        curveTo(21.1f, 18f, 22f, 17.1f, 22f, 16f)
        lineTo(22f, 4f)
        curveTo(22f, 2.9f, 21.1f, 2f, 20f, 2f)
        close()
        moveTo(6f, 9f)
        lineTo(18f, 9f)
        lineTo(18f, 11f)
        lineTo(6f, 11f)
        close()
        moveTo(6f, 13f)
        lineTo(14f, 13f)
        lineTo(14f, 15f)
        lineTo(6f, 15f)
        close()
    }
}.build()

enum class CaseDetailTab {
    OVERVIEW,
    TIMELINE
}

data class TimelineStep(
    val title: String,
    val time: String,
    val description: String,
    val isCompleted: Boolean,
    val isCurrent: Boolean
)

@Composable
fun CaseDetailsScreen(
    reportId: String = "#CEGCT-2024-0042",
    onNavigateToChat: () -> Unit,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(CaseDetailTab.OVERVIEW) }

    val timelineSteps = listOf(
        TimelineStep(
            title = "Report Submitted",
            time = "14 Nov 2024 • 10:32 AM",
            description = "Incident captured and logged as case $reportId.",
            isCompleted = true,
            isCurrent = false
        ),
        TimelineStep(
            title = "Under Investigation",
            time = "15 Nov 2024 • 09:14 AM",
            description = "Escalated to CEGCT Regional Officer Joseph Banda.",
            isCompleted = true,
            isCurrent = true
        ),
        TimelineStep(
            title = "Field Inspection Scheduled",
            time = "Pending Scheduled Inspection",
            description = "Inspector team dispatched to collect water & soil samples.",
            isCompleted = false,
            isCurrent = false
        ),
        TimelineStep(
            title = "Resolution & Enforcement",
            time = "Pending Final Review",
            description = "Enforcement order and compliance action.",
            isCompleted = false,
            isCurrent = false
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header Section
            GreenCurvedHeader(
                title = "Case Details",
                subtitle = "Case ID: $reportId",
                showNotificationBell = false,
                showBackArrow = true,
                onBack = onBack
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Hero Card Banner
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = DarkGreenHeader,
                    shadowElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = Color.White.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = reportId,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        ),
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = Color(0xFFFF9800)
                                ) {
                                    Text(
                                        text = "In Progress",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        ),
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Industrial Waste Discharge in Stream",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Area 25 Sector 3, Lilongwe • Filed 14 Nov 2024",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Segmented Tab Selector
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        CaseDetailTab.entries.forEach { tab ->
                            val isSelected = selectedTab == tab
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) DarkGreenHeader else Color.Transparent)
                                    .clickable { selectedTab = tab }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = when (tab) {
                                        CaseDetailTab.OVERVIEW -> "Overview"
                                        CaseDetailTab.TIMELINE -> "Timeline"
                                    },
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Color.White else Color(0xFF555555)
                                    )
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tab Content Switcher
                when (selectedTab) {
                    CaseDetailTab.OVERVIEW -> {
                        // 1. Overview Tab
                        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                            // Quick Badges
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1)),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("CATEGORY", style = MaterialTheme.typography.labelSmall, color = Color(0xFF00796B))
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text("Water Pollution", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFF004D40)))
                                    }
                                }

                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("URGENCY", style = MaterialTheme.typography.labelSmall, color = Color(0xFFD32F2F))
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text("High Urgency", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFFB71C1C)))
                                    }
                                }
                            }

                            // Incident Description Card
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Incident Description",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color(0xFF111111)
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "Dark oily chemical discharge observed spilling into the stream near the industrial outlet. Strong pungent smell and visible discoloration present along a 200m stretch.",
                                        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                                        color = Color(0xFF444444)
                                    )
                                }
                            }

                            // Assigned Officer Card
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
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFE8F3EE)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Officer",
                                            tint = DarkGreenHeader,
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(14.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Inspector Joseph Banda",
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = Color(0xFF111111)
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "CEGCT Lilongwe Regional Office",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF666666)
                                        )
                                    }

                                    Button(
                                        onClick = onNavigateToChat,
                                        colors = ButtonDefaults.buttonColors(containerColor = DarkGreenHeader),
                                        shape = RoundedCornerShape(10.dp),
                                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                                    ) {
                                        Icon(imageVector = ChatIcon, contentDescription = "Chat", modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Chat", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                                    }
                                }
                            }
                        }
                    }

                    CaseDetailTab.TIMELINE -> {
                        // 2. Timeline Tab
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = "Case Audit Progress",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFF111111)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                timelineSteps.forEachIndexed { index, step ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        // Step Indicator Column
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.width(28.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .clip(CircleShape)
                                                    .background(
                                                        when {
                                                            step.isCompleted -> DarkGreenHeader
                                                            step.isCurrent -> Color(0xFFFF9800)
                                                            else -> Color(0xFFE0E0E0)
                                                        }
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = if (step.isCompleted) "✓" else (index + 1).toString(),
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White
                                                    )
                                                )
                                            }

                                            if (index < timelineSteps.size - 1) {
                                                Box(
                                                    modifier = Modifier
                                                        .width(2.dp)
                                                        .height(50.dp)
                                                        .background(if (step.isCompleted) DarkGreenHeader else Color(0xFFE0E0E0))
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.width(12.dp))

                                        // Step Details
                                        Column(modifier = Modifier.padding(bottom = 18.dp)) {
                                            Text(
                                                text = step.title,
                                                style = MaterialTheme.typography.titleSmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (step.isCompleted || step.isCurrent) Color(0xFF111111) else Color(0xFF888888)
                                                )
                                            )

                                            Spacer(modifier = Modifier.height(2.dp))

                                            Text(
                                                text = step.time,
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = DarkGreenHeader
                                                )
                                            )

                                            Spacer(modifier = Modifier.height(4.dp))

                                            Text(
                                                text = step.description,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color(0xFF666666)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Primary Bottom Action: Chat with Assigned Officer
                Button(
                    onClick = onNavigateToChat,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGreenHeader),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(imageVector = ChatIcon, contentDescription = "Chat", modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Chat with Officer Joseph Banda",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
