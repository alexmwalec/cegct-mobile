package com.example.cegct.features.impact

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

private val AssignmentIcon: ImageVector = ImageVector.Builder(
    name = "Assignment",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(Color(0xFF0288D1))) {
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

private val CheckCircleIcon: ImageVector = ImageVector.Builder(
    name = "CheckCircle",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(DarkGreenHeader)) {
        moveTo(12f, 2f)
        curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
        curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
        curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
        curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
        close()
        moveTo(10f, 17f)
        lineTo(5f, 12f)
        lineTo(6.41f, 10.59f)
        lineTo(10f, 14.17f)
        lineTo(17.59f, 6.58f)
        lineTo(19f, 8f)
        lineTo(10f, 17f)
        close()
    }
}.build()

private val CategoryIcon: ImageVector = ImageVector.Builder(
    name = "Category",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(Color(0xFF7B1FA2))) {
        moveTo(12f, 2f)
        lineTo(4.5f, 11f)
        lineTo(19.5f, 11f)
        close()
        moveTo(12f, 13.5f)
        curveTo(9.51f, 13.5f, 7.5f, 15.51f, 7.5f, 18f)
        curveTo(7.5f, 20.49f, 9.51f, 22.5f, 12f, 22.5f)
        curveTo(14.49f, 22.5f, 16.5f, 20.49f, 16.5f, 18f)
        curveTo(16.5f, 15.51f, 14.49f, 13.5f, 12f, 13.5f)
        close()
    }
}.build()

private val WasteIcon: ImageVector = ImageVector.Builder(
    name = "Waste",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(DarkGreenHeader)) {
        moveTo(6f, 19f)
        curveTo(6f, 20.1f, 6.9f, 21f, 8f, 21f)
        lineTo(16f, 21f)
        curveTo(17.1f, 21f, 18f, 20.1f, 18f, 19f)
        lineTo(18f, 7f)
        lineTo(6f, 7f)
        lineTo(6f, 19f)
        close()
        moveTo(19f, 4f)
        lineTo(15.5f, 4f)
        lineTo(14.5f, 3f)
        lineTo(9.5f, 3f)
        lineTo(8.5f, 4f)
        lineTo(5f, 4f)
        lineTo(5f, 6f)
        lineTo(19f, 6f)
        lineTo(19f, 4f)
        close()
    }
}.build()

private val RecycleIcon: ImageVector = ImageVector.Builder(
    name = "Recycle",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(Color(0xFFEF6C00))) {
        moveTo(12f, 4f)
        lineTo(12f, 1f)
        lineTo(8f, 5f)
        lineTo(12f, 9f)
        lineTo(12f, 6f)
        curveTo(15.31f, 6f, 18f, 8.69f, 18f, 12f)
        curveTo(18f, 13.01f, 17.75f, 13.97f, 17.3f, 14.8f)
        lineTo(18.8f, 16.3f)
        curveTo(19.56f, 15.03f, 20f, 13.57f, 20f, 12f)
        curveTo(20f, 7.58f, 16.42f, 4f, 12f, 4f)
        close()
        moveTo(6f, 12f)
        curveTo(6f, 10.99f, 6.25f, 10.03f, 6.7f, 9.2f)
        lineTo(5.2f, 7.7f)
        curveTo(4.44f, 8.97f, 4f, 10.43f, 4f, 12f)
        curveTo(4f, 16.42f, 7.58f, 20f, 12f, 20f)
        lineTo(12f, 23f)
        lineTo(16f, 19f)
        lineTo(12f, 15f)
        lineTo(12f, 18f)
        curveTo(8.69f, 18f, 6f, 15.31f, 6f, 12f)
        close()
    }
}.build()

private val LandIcon: ImageVector = ImageVector.Builder(
    name = "Land",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(Color(0xFF5D4037))) {
        moveTo(14f, 6f)
        lineTo(9f, 13f)
        lineTo(6f, 9f)
        lineTo(2f, 18f)
        lineTo(22f, 18f)
        close()
    }
}.build()

private val Co2Icon: ImageVector = ImageVector.Builder(
    name = "Co2",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(Color(0xFF00796B))) {
        moveTo(17f, 8f)
        curveTo(17f, 8f, 13f, 2f, 6f, 5f)
        curveTo(6f, 5f, 3f, 12f, 9f, 17f)
        curveTo(13.5f, 20.75f, 19f, 17f, 19f, 17f)
        curveTo(19f, 17f, 21f, 11f, 17f, 8f)
        close()
    }
}.build()

private val HeartIcon: ImageVector = ImageVector.Builder(
    name = "Heart",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(Color(0xFFC62828))) {
        moveTo(12f, 21.35f)
        lineTo(10.55f, 20.03f)
        curveTo(5.4f, 15.36f, 2f, 12.28f, 2f, 8.5f)
        curveTo(2f, 5.42f, 4.42f, 3f, 7.5f, 3f)
        curveTo(9.24f, 3f, 10.91f, 3.81f, 12f, 5.09f)
        curveTo(13.09f, 3.81f, 14.76f, 3f, 16.5f, 3f)
        curveTo(19.58f, 3f, 22f, 5.42f, 22f, 8.5f)
        curveTo(22f, 12.28f, 18.6f, 15.36f, 13.45f, 20.04f)
        lineTo(12f, 21.35f)
        close()
    }
}.build()

private val TreeIcon: ImageVector = ImageVector.Builder(
    name = "Tree",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(DarkGreenHeader)) {
        moveTo(12f, 2f)
        lineTo(5f, 12f)
        lineTo(8f, 12f)
        lineTo(4f, 17f)
        lineTo(11f, 17f)
        lineTo(11f, 22f)
        lineTo(13f, 22f)
        lineTo(13f, 17f)
        lineTo(20f, 17f)
        lineTo(16f, 12f)
        lineTo(19f, 12f)
        close()
    }
}.build()

@Composable
fun ImpactScreen(
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
            // Header Banner
            GreenCurvedHeader(
                title = "Your Contribution & Impact",
                subtitle = "Track your environmental footprint and community metrics",
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
                // Section 1: Your Contributions Title
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = CheckCircleIcon,
                        contentDescription = "Contributions",
                        tint = DarkGreenHeader,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "Your Contributions",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp
                        ),
                        color = Color(0xFF111111)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 3 Top Stat Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Card 1: Total Reports
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color(0xFFE5E5E5)),
                        modifier = Modifier
                            .weight(1f)
                            .height(105.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE3F2FD)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = AssignmentIcon,
                                    contentDescription = "Total Reports",
                                    tint = Color(0xFF0288D1),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "0",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                ),
                                color = Color(0xFF111111)
                            )
                            Text(
                                text = "Total Reports",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                color = Color(0xFF666666)
                            )
                        }
                    }

                    // Card 2: Resolved
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color(0xFFE5E5E5)),
                        modifier = Modifier
                            .weight(1f)
                            .height(105.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE8F5E9)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = CheckCircleIcon,
                                    contentDescription = "Resolved",
                                    tint = DarkGreenHeader,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "0",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                ),
                                color = Color(0xFF111111)
                            )
                            Text(
                                text = "Resolved",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                color = Color(0xFF666666)
                            )
                        }
                    }

                    // Card 3: Categories
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color(0xFFE5E5E5)),
                        modifier = Modifier
                            .weight(1f)
                            .height(105.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF3E5F5)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = CategoryIcon,
                                    contentDescription = "Categories",
                                    tint = Color(0xFF7B1FA2),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "0",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                ),
                                color = Color(0xFF111111)
                            )
                            Text(
                                text = "Categories",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                color = Color(0xFF666666)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Resolution Rate Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E5E5)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Resolution Rate",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                ),
                                color = Color(0xFF111111)
                            )

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFE8F5E9)
                            ) {
                                Text(
                                    text = "0%",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = DarkGreenHeader,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        LinearProgressIndicator(
                            progress = { 0f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = DarkGreenHeader,
                            trackColor = Color(0xFFE0E0E0)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Section 2: Environmental Impact Title
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Co2Icon,
                        contentDescription = "Impact",
                        tint = DarkGreenHeader,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "Environmental Impact",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp
                        ),
                        color = Color(0xFF111111)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 2x3 Metric Cards Grid
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Row 1
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MetricCard(
                            title = "Total Waste",
                            value = "0 kg",
                            subtitle = "Tagged waste",
                            icon = WasteIcon,
                            iconTint = DarkGreenHeader,
                            bgColor = Color(0xFFE8F5E9),
                            modifier = Modifier.weight(1f)
                        )
                        MetricCard(
                            title = "Plastic Waste",
                            value = "0 kg",
                            subtitle = "Prevented pollution",
                            icon = RecycleIcon,
                            iconTint = Color(0xFFEF6C00),
                            bgColor = Color(0xFFFFF3E0),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Row 2
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MetricCard(
                            title = "Land Preserved",
                            value = "0 sq.m",
                            subtitle = "Soil & hills protected",
                            icon = LandIcon,
                            iconTint = Color(0xFF5D4037),
                            bgColor = Color(0xFFEFEBE9),
                            modifier = Modifier.weight(1f)
                        )
                        MetricCard(
                            title = "CO₂ Prevented",
                            value = "0 kg",
                            subtitle = "Helped carbon reduction",
                            icon = Co2Icon,
                            iconTint = Color(0xFF00796B),
                            bgColor = Color(0xFFE0F2F1),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Row 3
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MetricCard(
                            title = "Lives Saved",
                            value = "0",
                            subtitle = "Wildlife & pets protected",
                            icon = HeartIcon,
                            iconTint = Color(0xFFC62828),
                            bgColor = Color(0xFFFFEBEE),
                            modifier = Modifier.weight(1f)
                        )
                        MetricCard(
                            title = "Trees Saved",
                            value = "0",
                            subtitle = "Forests & greenery preserved",
                            icon = TreeIcon,
                            iconTint = DarkGreenHeader,
                            bgColor = Color(0xFFE8F5E9),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    bgColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E5E5)),
        modifier = modifier.height(120.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    ),
                    color = Color(0xFF666666)
                )

                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(bgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconTint,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                ),
                color = DarkGreenHeader
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                color = Color(0xFF888888)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImpactScreenPreview() {
    ImpactScreen()
}
