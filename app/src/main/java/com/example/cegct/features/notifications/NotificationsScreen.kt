package com.example.cegct.features.notifications

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
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cegct.core.firestore.FirestoreRepository
import com.example.cegct.core.firestore.FirestoreRepositoryImpl
import com.example.cegct.core.firestore.models.ReportEntity
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader

private val DarkGreenHeader = Color(0xFF185835)
private val ScreenBackground = Color(0xFFE5F0EE)

private val NotificationBellIcon: ImageVector = ImageVector.Builder(
    name = "NotificationBell",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(DarkGreenHeader)) {
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

data class NotificationItemData(
    val id: String,
    val title: String,
    val caseId: String,
    val message: String,
    val timeTime: String,
    val statusTag: String,
    val statusColor: Color,
    val statusBg: Color,
    val isRead: Boolean
)

@Composable
fun NotificationsScreen(
    onSelectNotification: (String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    val repository: FirestoreRepository = remember { FirestoreRepositoryImpl() }
    val realReports by repository.observeAllReports().collectAsState(initial = emptyList())
    var filterUnreadOnly by remember { mutableStateOf(false) }

    // Convert real-time report objects into live Notification items
    val liveNotifications = remember(realReports) {
        realReports.map { report ->
            val statusClean = report.status.uppercase()
            val (statusColor, statusBg) = when {
                statusClean.contains("RESOLVE") -> Color(0xFF2E7D32) to Color(0xFFE8F5E9)
                statusClean.contains("PROGRESS") -> Color(0xFFEF6C00) to Color(0xFFFFF3E0)
                else -> Color(0xFF00838F) to Color(0xFFE0F7FA)
            }

            NotificationItemData(
                id = report.reportId,
                title = report.title.ifBlank { "Report: ${report.category}" },
                caseId = report.reportId,
                message = "Status is '${report.status}' for incident at ${report.address.ifBlank { "reported location" }}.",
                timeTime = if (report.createdAt != null && report.createdAt > 0) "Reported #${report.reportId.take(8)}" else "Recently",
                statusTag = report.status.ifBlank { "PENDING" },
                statusColor = statusColor,
                statusBg = statusBg,
                isRead = statusClean.contains("RESOLVE")
            )
        }
    }

    val filteredList = if (filterUnreadOnly) {
        liveNotifications.filter { !it.isRead }
    } else {
        liveNotifications
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
            // Dark Green Header Banner
            GreenCurvedHeader(
                title = "Notifications Inbox",
                subtitle = "Real-time status updates on environmental reports",
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
                // Header Action Bar: Count & Filter Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${filteredList.size} Notifications",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        color = Color(0xFF1A1A1A)
                    )

                    TextButton(onClick = { filterUnreadOnly = !filterUnreadOnly }) {
                        Text(
                            text = if (filterUnreadOnly) "Show All" else "Unread Only",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = DarkGreenHeader
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // EMPTY STATE
                if (filteredList.isEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 48.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(84.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = NotificationBellIcon,
                                contentDescription = "No Notifications",
                                tint = DarkGreenHeader,
                                modifier = Modifier.size(42.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "No Notifications Found",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            color = Color(0xFF1A1A1A)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "You're all caught up! Real-time updates for submitted environmental reports will appear here.",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                            color = Color(0xFF666666),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                } else {
                    // NOTIFICATION CARDS LIST
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        filteredList.forEach { item ->
                            Card(
                                onClick = { onSelectNotification(item.caseId) },
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
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
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            if (!item.isRead) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(8.dp)
                                                        .clip(CircleShape)
                                                        .background(DarkGreenHeader)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                            }

                                            Text(
                                                text = item.title,
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 16.sp
                                                ),
                                                color = Color(0xFF111111)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Surface(
                                            shape = RoundedCornerShape(12.dp),
                                            color = item.statusBg
                                        ) {
                                            Text(
                                                text = item.statusTag,
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp
                                                ),
                                                color = item.statusColor,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = item.caseId,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        ),
                                        color = DarkGreenHeader
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = item.message,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = 14.sp,
                                            color = Color(0xFF444444)
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = item.timeTime,
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                            color = Color(0xFF888888)
                                        )

                                        Text(
                                            text = "View details →",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = DarkGreenHeader
                                            )
                                        )
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

@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    NotificationsScreen()
}
