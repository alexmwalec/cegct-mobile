package com.example.cegct.features.auth.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cegct.features.auth.ui.AuthStrings

enum class PasswordStrengthLevel(val score: Int) {
    EMPTY(0),
    WEAK(1),
    MEDIUM(2),
    GOOD(3),
    STRONG(4)
}

fun calculatePasswordStrength(password: String): PasswordStrengthLevel {
    if (password.isEmpty()) return PasswordStrengthLevel.EMPTY
    var score = 0
    if (password.length >= 8) score++
    if (password.contains(Regex("[A-Z]")) && password.contains(Regex("[a-z]"))) score++
    if (password.contains(Regex("[0-9]"))) score++
    if (password.contains(Regex("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]"))) score++

    return when (score) {
        0, 1 -> PasswordStrengthLevel.WEAK
        2 -> PasswordStrengthLevel.MEDIUM
        3 -> PasswordStrengthLevel.GOOD
        4 -> PasswordStrengthLevel.STRONG
        else -> PasswordStrengthLevel.STRONG
    }
}

@Composable
fun PasswordStrengthMeter(
    password: String,
    strings: AuthStrings,
    modifier: Modifier = Modifier
) {
    val level = calculatePasswordStrength(password)

    if (password.isEmpty()) return

    val (labelText, targetColor) = when (level) {
        PasswordStrengthLevel.EMPTY -> "" to Color.Transparent
        PasswordStrengthLevel.WEAK -> strings.weak to Color(0xFFE53935)
        PasswordStrengthLevel.MEDIUM -> strings.medium to Color(0xFFFB8C00)
        PasswordStrengthLevel.GOOD -> strings.good to Color(0xFF7CB342)
        PasswordStrengthLevel.STRONG -> strings.strong to Color(0xFF2E7D32)
    }

    val animatedColor by animateColorAsState(targetValue = targetColor, label = "StrengthColor")

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            for (i in 1..4) {
                val isActive = i <= level.score
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(if (isActive) animatedColor else Color.LightGray.copy(alpha = 0.4f))
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${strings.passwordStrengthLabel}: ",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = labelText,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = animatedColor
            )
        }
    }
}
