package com.example.cegct.features.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cegct.ui.theme.GreenGradientEnd
import com.example.cegct.ui.theme.GreenGradientStart
import kotlinx.coroutines.delay

// Leaf logo icon
private val LeafLogoIcon: ImageVector = ImageVector.Builder(
    name = "LeafLogo",
    defaultWidth = 64.dp,
    defaultHeight = 64.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(Color(0xFF2E7D32))) {
        moveTo(17f, 8f)
        curveTo(17f, 8f, 13f, 2f, 6f, 5f)
        curveTo(6f, 5f, 3f, 12f, 9f, 17f)
        curveTo(13.5f, 20.75f, 19f, 17f, 19f, 17f)
        curveTo(19f, 17f, 21f, 11f, 17f, 8f)
        close()
    }
    path(fill = null, stroke = SolidColor(Color.White), strokeLineWidth = 2f) {
        moveTo(5f, 19f)
        lineTo(12f, 12f)
        moveTo(12f, 12f)
        lineTo(16f, 10f)
        moveTo(12f, 12f)
        lineTo(9f, 15f)
    }
}.build()

@Composable
fun WelcomeScreen(
    viewModel: AuthViewModel = AuthViewModel(),
    onNavigateToLogin: () -> Unit
) {
    // Automatically navigate to Login screen after 1.5 seconds
    LaunchedEffect(Unit) {
        delay(1500)
        onNavigateToLogin()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GreenGradientStart, GreenGradientEnd)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = LeafLogoIcon,
                    contentDescription = "CEGCT Leaf Logo",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(72.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "CEGCT",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = 2.sp
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}
