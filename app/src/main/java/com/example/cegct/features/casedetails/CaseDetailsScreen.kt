package com.example.cegct.features.casedetails

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader

private val DarkGreenHeader = Color(0xFF185835)
private val ScreenBackground = Color(0xFFE5F0EE)

private val WaterIcon: ImageVector = ImageVector.Builder("Water", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color(0xFF00838F))) {
        moveTo(12f, 2.69f)
        lineTo(5.21f, 10.22f)
        curveTo(3.18f, 12.5f, 3.18f, 15.93f, 5.21f, 18.21f)
        curveTo(7.24f, 20.49f, 10.54f, 20.49f, 12.57f, 18.21f)
        curveTo(14.6f, 15.93f, 14.6f, 12.5f, 12.57f, 10.22f)
        lineTo(12f, 2.69f)
        close()
    }
}.build()

private val WarningIcon: ImageVector = ImageVector.Builder("Warning", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color(0xFFC62828))) {
        moveTo(1f, 21f)
        lineTo(23f, 21f)
        lineTo(12f, 2f)
        lineTo(1f, 21f)
        close()
        moveTo(13f, 18f)
        lineTo(11f, 18f)
        lineTo(11f, 16f)
        lineTo(13f, 16f)
        lineTo(13f, 18f)
        close()
        moveTo(13f, 14f)
        lineTo(11f, 14f)
        lineTo(11f, 10f)
        lineTo(13f, 10f)
        lineTo(13f, 14f)
        close()
    }
}.build()

private val InProgressIcon: ImageVector = ImageVector.Builder("InProgress", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color(0xFFEF6C00))) {
        moveTo(12f, 2f)
        curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
        curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
        curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
        curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
        close()
        moveTo(13f, 13f)
        lineTo(7f, 13f)
        lineTo(7f, 11f)
        lineTo(11f, 11f)
        lineTo(11f, 7f)
        lineTo(13f, 7f)
        lineTo(13f, 13f)
        close()
    }
}.build()

private val LocationIcon: ImageVector = ImageVector.Builder("Location", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(DarkGreenHeader)) {
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

private val PersonIcon: ImageVector = ImageVector.Builder("Person", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color(0xFF555555))) {
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

private val AuthorityIcon: ImageVector = ImageVector.Builder("Authority", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(Color(0xFF555555))) {
        moveTo(12f, 1f)
        lineTo(3f, 5f)
        lineTo(3f, 11f)
        curveTo(3f, 16.55f, 6.84f, 21.74f, 12f, 23f)
        curveTo(17.16f, 21.74f, 21f, 16.55f, 21f, 11f)
        lineTo(21f, 5f)
        lineTo(12f, 1f)
        close()
    }
}.build()

private val CheckIcon: ImageVector = ImageVector.Builder("Check", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(DarkGreenHeader)) {
        moveTo(9f, 16.17f)
        lineTo(4.83f, 12f)
        lineTo(3.41f, 13.41f)
        lineTo(9f, 19f)
        lineTo(21f, 7.34f)
        lineTo(19.59f, 5.93f)
        close()
    }
}.build()

private val ChatIcon: ImageVector = ImageVector.Builder("Chat", 24.dp, 24.dp, 24f, 24f).apply {
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
    }
}.build()

@Composable
fun CaseDetailsScreen(
    reportId: String = "#CEGCT-2024-0042",
    onNavigateToChat: () -> Unit = {},
    onBack: () -> Unit = {}
) {
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
            // Header
            GreenCurvedHeader(
                title = "Report Details",
                subtitle = "Case Ref: $reportId",
                showNotificationBell = false,
                showBackArrow = true,
                onBack = onBack
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Top Case Header Card with Status Badge
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E5E5)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = reportId,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 18.sp
                                    ),
                                    color = DarkGreenHeader
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = "Filed 14 Nov 2024",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                                    color = Color(0xFF757575)
                                )
                            }

                            // Status Badge Properly Positioned on Top Right
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFFFF3E0)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = InProgressIcon,
                                        contentDescription = "Status",
                                        tint = Color(0xFFEF6C00),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "In Progress",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        ),
                                        color = Color(0xFFEF6C00)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Category & Severity Badges
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFE0F7FA)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = WaterIcon,
                                        contentDescription = "Water",
                                        tint = Color(0xFF00838F),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Water Contamination",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        ),
                                        color = Color(0xFF00838F)
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFFFEBEE)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = WarningIcon,
                                        contentDescription = "Severity",
                                        tint = Color(0xFFC62828),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "High Severity",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        ),
                                        color = Color(0xFFC62828)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Title & Incident Description Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E5E5)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Industrial discharge observed in Ngong River tributary",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            ),
                            color = Color(0xFF111111)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Dark oily discharge with foul odor visible entering the stream near the old tannery site. Fish kills observed downstream. Potential source: upstream factory outlet.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 14.sp,
                                color = Color(0xFF444444)
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = Color(0xFFEEEEEE))
                        Spacer(modifier = Modifier.height(16.dp))

                        // Metadata List with Material Icons
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = LocationIcon,
                                    contentDescription = "Location",
                                    tint = DarkGreenHeader,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Ngong River Rd, Nairobi • 1.3 km away",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                                    color = Color(0xFF333333)
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = PersonIcon,
                                    contentDescription = "Reported By",
                                    tint = Color(0xFF555555),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Reported By: Anonymous Citizen",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                                    color = Color(0xFF333333)
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = AuthorityIcon,
                                    contentDescription = "Assigned To",
                                    tint = Color(0xFF555555),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Assigned To: NEMA Regional Office",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = DarkGreenHeader
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Case Progress Timeline Header
                Text(
                    text = "Case Progress Timeline",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    color = Color(0xFF111111)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Timeline Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E5E5)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Step 1: Submitted
                        Row(verticalAlignment = Alignment.Top) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE8F5E9)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = CheckIcon,
                                    contentDescription = "Completed",
                                    tint = DarkGreenHeader,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = "Report Submitted",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    ),
                                    color = DarkGreenHeader
                                )
                                Text(
                                    text = "14 Nov 2024 - 10:32 AM",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                    color = Color(0xFF888888)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Your report was received and assigned case number #CEGCT-2024-0042.",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                                    color = Color(0xFF555555)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Step 2: Under Review (Active Step)
                        Row(verticalAlignment = Alignment.Top) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFFF3E0)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = InProgressIcon,
                                    contentDescription = "In Progress",
                                    tint = Color(0xFFEF6C00),
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = "Under Review & Assigned",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    ),
                                    color = Color(0xFFEF6C00)
                                )
                                Text(
                                    text = "15 Nov 2024 - 09:14 AM",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                    color = Color(0xFF888888)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Report reviewed and escalated to NEMA regional office for field assessment.",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                                    color = Color(0xFF555555)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Step 3: Pending Action
                        Row(verticalAlignment = Alignment.Top) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF2F2F2)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = AuthorityIcon,
                                    contentDescription = "Pending",
                                    tint = Color(0xFF888888),
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = "Field Inspection & Resolution",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    ),
                                    color = Color(0xFF888888)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Pending field team dispatch and environmental cleanup.",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                                    color = Color(0xFF888888)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Chat / Contact Officer Button
                Button(
                    onClick = onNavigateToChat,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreenHeader,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = ChatIcon,
                            contentDescription = "Chat",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Chat / Contact Assigned Officer",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CaseDetailsScreenPreview() {
    CaseDetailsScreen()
}
