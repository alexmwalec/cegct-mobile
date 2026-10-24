package com.example.cegct.features.notifications

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cegct.core.network.NotificationDto
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

@Composable
fun NotificationsScreen(
    onSelectNotification: (String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    val notifRepository = remember { NotificationsRepository() }
    val notifications by notifRepository.notifications.collectAsState()
    val isLoading by notifRepository.isLoading.collectAsState()
    val errorMsg by notifRepository.error.collectAsState()

    var filterUnreadOnly by remember { mutableStateOf(false) }

    val filteredList = remember(notifications, filterUnreadOnly) {
        if (filterUnreadOnly) {
            notifications.filter { !it.isRead }
        } else {
            notifications
        }
    }

    val unreadCount = remember(notifications) {
        notifications.count { !it.isRead }
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
                subtitle = if (unreadCount > 0) "$unreadCount unread notification${if (unreadCount > 1) "s" else ""}" else "All notifications up to date",
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
                // Error Bar
                errorMsg?.let { err ->
                    Surface(
                        color = Color(0xFFFFEBEE),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Text(
                            text = err,
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFD32F2F)),
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

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

                // LOADING STATE
                if (isLoading && notifications.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = DarkGreenHeader)
                    }
                }
                // EMPTY STATE
                else if (filteredList.isEmpty()) {
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
                            text = "You're all caught up! Official replies and status updates for submitted environmental reports will appear here.",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                            color = Color(0xFF666666),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                } else {
                    // NOTIFICATION CARDS LIST
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        filteredList.forEach { notif ->
                            NotificationCard(
                                notif = notif,
                                onClick = {
                                    notifRepository.markAsRead(notif.effectiveId) {
                                        val targetReportId = notif.effectiveReportId
                                        if (targetReportId.isNotBlank()) {
                                            onSelectNotification(targetReportId)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun NotificationCard(
    notif: NotificationDto,
    onClick: () -> Unit
) {
    val isReply = notif.isAdminReply
    val typeBadgeBg = if (isReply) Color(0xFFE0F2F1) else Color(0xFFFFF3E0)
    val typeBadgeText = if (isReply) Color(0xFF00796B) else Color(0xFFE65100)
    val typeLabel = if (isReply) "ADMIN REPLY" else "STATUS UPDATE"

    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
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
                    if (!notif.isRead) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(DarkGreenHeader)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Text(
                        text = notif.effectiveTitle,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = if (!notif.isRead) FontWeight.ExtraBold else FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        color = Color(0xFF111111)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = typeBadgeBg
                ) {
                    Text(
                        text = typeLabel,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        ),
                        color = typeBadgeText,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            if (notif.effectiveReportId.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Report Case #${notif.effectiveReportId}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    ),
                    color = DarkGreenHeader
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = notif.effectiveBody,
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
                    text = notif.effectiveCreatedAt,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                    color = Color(0xFF888888)
                )

                Text(
                    text = "View details & chat →",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkGreenHeader
                    )
                )
            }
        }
    }
}
