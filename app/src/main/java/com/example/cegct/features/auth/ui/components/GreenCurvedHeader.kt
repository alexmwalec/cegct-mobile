package com.example.cegct.features.auth.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

private val DarkGreenHeader = Color(0xFF185835)

private val BackArrowIcon: ImageVector = ImageVector.Builder(
    name = "BackArrow",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = null, stroke = SolidColor(Color.White), strokeLineWidth = 2.5f) {
        moveTo(20f, 12f)
        lineTo(4f, 12f)
        moveTo(10f, 18f)
        lineTo(4f, 12f)
        lineTo(10f, 6f)
    }
}.build()

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

@Composable
fun GreenCurvedHeader(
    title: String,
    subtitle: String? = null,
    showNotificationBell: Boolean = false,
    showBackArrow: Boolean = false,
    onBack: (() -> Unit)? = null
) {
    Surface(
        color = DarkGreenHeader,
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (showBackArrow && onBack != null) {
                        Icon(
                            imageVector = BackArrowIcon,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier
                                .size(22.dp)
                                .clickable { onBack() }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }

                    Text("🌍", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "CEGCT",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    )
                }

                if (showNotificationBell) {
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
            }

            if (title.isNotEmpty()) {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    ),
                    color = Color.White
                )
            }

            if (!subtitle.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}
