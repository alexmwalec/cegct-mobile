package com.example.cegct.features.casedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cegct.core.firestore.FirestoreRepository
import com.example.cegct.core.firestore.FirestoreRepositoryImpl
import com.example.cegct.core.firestore.models.ReportEntity
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader
import com.example.cegct.features.chat.ChatMessageBubble
import com.example.cegct.features.chat.ChatRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val DarkGreenHeader = Color(0xFF185835)
private val ScreenBg = Color(0xFFE5F0EE)

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

private fun formatTimestamp(timestamp: Long?): String {
    if (timestamp == null || timestamp == 0L) return "Recently reported"
    return try {
        val sdf = SimpleDateFormat("dd MMM yyyy • hh:mm a", Locale.getDefault())
        sdf.format(Date(timestamp))
    } catch (_: Exception) {
        "Recently reported"
    }
}

@Composable
fun CaseDetailsScreen(
    reportId: String = "#CEGCT-2024-0042",
    onNavigateToChat: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(CaseDetailTab.OVERVIEW) }
    val repository: FirestoreRepository = remember { FirestoreRepositoryImpl() }
    var reportEntity by remember { mutableStateOf<ReportEntity?>(null) }

    val chatRepository = remember(reportId) { ChatRepository(reportId) }
    val chatMessages by chatRepository.messages.collectAsState()
    val isChatLoading by chatRepository.isLoading.collectAsState()

    var quickReplyText by remember { mutableStateOf("") }
    var isSendingQuickReply by remember { mutableStateOf(false) }

    LaunchedEffect(reportId) {
        val res = repository.getReportDetails(reportId)
        if (res.isSuccess) {
            reportEntity = res.getOrNull()
        }
    }

    val report = reportEntity
    val currentStatus = report?.status?.uppercase() ?: "SUBMITTED"
    val reportedTimeStr = formatTimestamp(report?.createdAt)
    val updatedTimeStr = formatTimestamp(report?.updatedAt ?: report?.createdAt)

    val timelineSteps = remember(report, currentStatus, reportedTimeStr, updatedTimeStr) {
        listOf(
            TimelineStep(
                title = "Report Submitted",
                time = reportedTimeStr,
                description = "Incident captured and logged under case $reportId.",
                isCompleted = true,
                isCurrent = currentStatus == "SUBMITTED"
            ),
            TimelineStep(
                title = "Under Investigation",
                time = if (currentStatus != "SUBMITTED") updatedTimeStr else "Pending Investigation",
                description = if (report?.assignedInspectorName.isNullOrBlank()) {
                    "Assigned to CEGCT Environmental Assessment Team for evaluation."
                } else {
                    "Assigned to Inspector ${report.assignedInspectorName} for regional review."
                },
                isCompleted = currentStatus in listOf("UNDER_REVIEW", "INSPECTION_SCHEDULED", "IN_PROGRESS", "RESOLVED"),
                isCurrent = currentStatus == "UNDER_REVIEW"
            ),
            TimelineStep(
                title = "Field Inspection Scheduled",
                time = if (currentStatus in listOf("INSPECTION_SCHEDULED", "IN_PROGRESS", "RESOLVED")) "Inspection Dispatched" else "Pending Scheduled Inspection",
                description = "Field inspection team dispatched to collect environmental evidence at ${report?.address?.ifBlank { "reported location" } ?: "reported location"}.",
                isCompleted = currentStatus in listOf("IN_PROGRESS", "RESOLVED"),
                isCurrent = currentStatus in listOf("INSPECTION_SCHEDULED", "IN_PROGRESS")
            ),
            TimelineStep(
                title = "Resolution & Enforcement",
                time = if (currentStatus == "RESOLVED") formatTimestamp(report?.resolvedAt ?: report?.updatedAt) else "Pending Final Review",
                description = report?.resolutionDetails?.ifBlank { null }
                    ?: if (currentStatus == "RESOLVED") "Environmental issue resolved and compliance verified." else "Enforcement order and compliance review.",
                isCompleted = currentStatus == "RESOLVED",
                isCurrent = currentStatus == "RESOLVED"
            )
        )
    }

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

                                val statusBg = when (currentStatus) {
                                    "RESOLVED" -> Color(0xFF2E7D32)
                                    "IN_PROGRESS", "INSPECTION_SCHEDULED" -> Color(0xFFFF9800)
                                    "UNDER_REVIEW" -> Color(0xFF0288D1)
                                    else -> Color(0xFF00838F)
                                }

                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = statusBg
                                ) {
                                    Text(
                                        text = currentStatus.replace("_", " "),
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
                                text = report?.title?.ifBlank { report.category } ?: "Environmental Incident Report",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Location",
                                    tint = Color.White.copy(alpha = 0.9f),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${report?.address?.ifBlank { "Malawi" } ?: "Malawi"} • $reportedTimeStr",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White.copy(alpha = 0.85f)
                                    )
                                )
                            }
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
                                        Text(
                                            text = report?.category?.ifBlank { "Environmental" } ?: "Environmental",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFF004D40))
                                        )
                                    }
                                }

                                val urgencyColor = when (report?.urgency?.uppercase()) {
                                    "CRITICAL", "HIGH" -> Color(0xFFD32F2F)
                                    "LOW" -> Color(0xFF2E7D32)
                                    else -> Color(0xFFE65100)
                                }
                                val urgencyBg = when (report?.urgency?.uppercase()) {
                                    "CRITICAL", "HIGH" -> Color(0xFFFFEBEE)
                                    "LOW" -> Color(0xFFE8F5E9)
                                    else -> Color(0xFFFFF3E0)
                                }

                                Card(
                                    colors = CardDefaults.cardColors(containerColor = urgencyBg),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("URGENCY", style = MaterialTheme.typography.labelSmall, color = urgencyColor)
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "${report?.urgency?.ifBlank { "MEDIUM" } ?: "MEDIUM"} Urgency",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = urgencyColor)
                                        )
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
                                        text = report?.description?.ifBlank { "No detailed description provided." } ?: "No detailed description provided.",
                                        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                                        color = Color(0xFF444444)
                                    )
                                }
                            }

                            // Citizen Evidence Photo Card (if available)
                            val firstImageUrl = report?.imageUrls?.firstOrNull()
                            if (!firstImageUrl.isNullOrBlank()) {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = "Citizen Evidence Photo",
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = Color(0xFF111111)
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        AsyncImage(
                                            model = firstImageUrl,
                                            contentDescription = "Evidence Photo",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(220.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                        )
                                    }
                                }
                            }

                            // Live Report Conversation & Admin Replies Section
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = "Official Admin Conversation",
                                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                                color = Color(0xFF111111)
                                            )
                                            Text(
                                                text = "Real-time responses from CEGCT Web Dashboard",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color.Gray
                                            )
                                        }

                                        TextButton(onClick = onNavigateToChat) {
                                            Text(
                                                text = "Full View →",
                                                style = MaterialTheme.typography.labelMedium.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = DarkGreenHeader
                                                )
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    if (isChatLoading && chatMessages.isEmpty()) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(80.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator(color = DarkGreenHeader)
                                        }
                                    } else if (chatMessages.isEmpty()) {
                                        Surface(
                                            shape = RoundedCornerShape(12.dp),
                                            color = Color(0xFFF5F5F5),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "No messages yet. Send a response below to contact CEGCT official admin team regarding case $reportId.",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color.Gray,
                                                modifier = Modifier.padding(12.dp)
                                            )
                                        }
                                    } else {
                                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                            chatMessages.takeLast(5).forEach { msg ->
                                                ChatMessageBubble(msg = msg, reportId = reportId)
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(14.dp))

                                    // Quick Reply Box
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        OutlinedTextField(
                                            value = quickReplyText,
                                            onValueChange = { quickReplyText = it },
                                            placeholder = { Text("Reply for case $reportId...") },
                                            shape = RoundedCornerShape(12.dp),
                                            singleLine = true,
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = DarkGreenHeader,
                                                unfocusedBorderColor = Color(0xFFCCCCCC)
                                            ),
                                            modifier = Modifier.weight(1f)
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Button(
                                            onClick = {
                                                val textToSend = quickReplyText.trim()
                                                if (textToSend.isNotBlank() && !isSendingQuickReply) {
                                                    isSendingQuickReply = true
                                                    chatRepository.sendMessage(textToSend) { success ->
                                                        isSendingQuickReply = false
                                                        if (success) {
                                                            quickReplyText = ""
                                                        }
                                                    }
                                                }
                                            },
                                            enabled = quickReplyText.isNotBlank() && !isSendingQuickReply,
                                            colors = ButtonDefaults.buttonColors(containerColor = DarkGreenHeader),
                                            shape = RoundedCornerShape(10.dp),
                                            modifier = Modifier.height(48.dp)
                                        ) {
                                            if (isSendingQuickReply) {
                                                CircularProgressIndicator(
                                                    modifier = Modifier.size(18.dp),
                                                    color = Color.White,
                                                    strokeWidth = 2.dp
                                                )
                                            } else {
                                                Text("Reply", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                                            }
                                        }
                                    }
                                }
                            }

                            // Reporter Info Card
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
                                            contentDescription = "Reporter",
                                            tint = DarkGreenHeader,
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(14.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Reported by ${report?.userName?.ifBlank { "Citizen" } ?: "Citizen"}",
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = Color(0xFF111111)
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "Reported on $reportedTimeStr",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF666666)
                                        )
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

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
