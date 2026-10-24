package com.example.cegct.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CegctAppLogo(
    modifier: Modifier = Modifier,
    size: Dp = 32.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.22f))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF4CAF50),
                        Color(0xFF00897B),
                        Color(0xFF0288D1)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size * 0.82f)) {
            val width = this.size.width
            val height = this.size.height

            // White outer circle ring
            drawCircle(
                color = Color.White,
                radius = width * 0.46f,
                center = Offset(width * 0.5f, height * 0.5f),
                style = Stroke(width = width * 0.08f)
            )

            // Sky background fill
            drawCircle(
                color = Color(0xFFE1F5FE),
                radius = width * 0.42f,
                center = Offset(width * 0.5f, height * 0.5f)
            )

            // Green Citizen Head
            drawCircle(
                color = Color(0xFF2E7D32),
                radius = width * 0.15f,
                center = Offset(width * 0.32f, height * 0.38f)
            )

            // Citizen body path
            val bodyPath = Path().apply {
                moveTo(width * 0.12f, height * 0.88f)
                cubicTo(
                    width * 0.12f, height * 0.55f,
                    width * 0.28f, height * 0.52f,
                    width * 0.42f, height * 0.62f
                )
                cubicTo(
                    width * 0.55f, height * 0.48f,
                    width * 0.65f, height * 0.58f,
                    width * 0.52f, height * 0.88f
                )
                close()
            }
            drawPath(path = bodyPath, color = Color(0xFF2E7D32))

            // Green Leaf at Bottom Right
            val leafPath = Path().apply {
                moveTo(width * 0.52f, height * 0.88f)
                cubicTo(
                    width * 0.55f, height * 0.52f,
                    width * 0.88f, height * 0.52f,
                    width * 0.92f, height * 0.85f
                )
                cubicTo(
                    width * 0.72f, height * 0.95f,
                    width * 0.58f, height * 0.92f,
                    width * 0.52f, height * 0.88f
                )
                close()
            }
            drawPath(path = leafPath, color = Color(0xFF388E3C))

            // Leaf vein line
            val veinPath = Path().apply {
                moveTo(width * 0.55f, height * 0.85f)
                quadraticTo(width * 0.72f, height * 0.68f, width * 0.88f, height * 0.58f)
            }
            drawPath(path = veinPath, color = Color.White, style = Stroke(width = width * 0.035f))

            // Blue Smartphone
            rotate(degrees = -12f, pivot = Offset(width * 0.55f, height * 0.38f)) {
                val phoneRect = Path().apply {
                    addRoundRect(
                        RoundRect(
                            rect = Rect(
                                left = width * 0.44f,
                                top = height * 0.22f,
                                right = width * 0.68f,
                                bottom = height * 0.56f
                            ),
                            cornerRadius = CornerRadius(width * 0.05f)
                        )
                    )
                }
                drawPath(path = phoneRect, color = Color(0xFF1565C0))

                // Phone Screen Inner White
                val screenRect = Path().apply {
                    addRoundRect(
                        RoundRect(
                            rect = Rect(
                                left = width * 0.47f,
                                top = height * 0.26f,
                                right = width * 0.65f,
                                bottom = height * 0.52f
                            ),
                            cornerRadius = CornerRadius(width * 0.02f)
                        )
                    )
                }
                drawPath(path = screenRect, color = Color.White)

                // Orange Location Pin on Phone Screen
                val pinCenter = Offset(width * 0.56f, height * 0.36f)
                val pinPath = Path().apply {
                    moveTo(pinCenter.x, pinCenter.y + height * 0.09f)
                    cubicTo(
                        pinCenter.x - width * 0.07f, pinCenter.y + height * 0.03f,
                        pinCenter.x - width * 0.07f, pinCenter.y - height * 0.05f,
                        pinCenter.x, pinCenter.y - height * 0.05f
                    )
                    cubicTo(
                        pinCenter.x + width * 0.07f, pinCenter.y - height * 0.05f,
                        pinCenter.x + width * 0.07f, pinCenter.y + height * 0.03f,
                        pinCenter.x, pinCenter.y + height * 0.09f
                    )
                    close()
                }
                drawPath(path = pinPath, color = Color(0xFFE65100))

                // Small White Leaf inside Orange Pin
                drawCircle(color = Color.White, radius = width * 0.022f, center = Offset(pinCenter.x, pinCenter.y - height * 0.01f))
            }

            // Orange Alert Rays (3 signal lines) top right
            val ray1 = Path().apply {
                moveTo(width * 0.68f, height * 0.22f)
                lineTo(width * 0.74f, height * 0.16f)
            }
            val ray2 = Path().apply {
                moveTo(width * 0.74f, height * 0.28f)
                lineTo(width * 0.82f, height * 0.25f)
            }
            val ray3 = Path().apply {
                moveTo(width * 0.64f, height * 0.14f)
                lineTo(width * 0.68f, height * 0.08f)
            }
            drawPath(path = ray1, color = Color(0xFFFF9800), style = Stroke(width = width * 0.05f))
            drawPath(path = ray2, color = Color(0xFFFF9800), style = Stroke(width = width * 0.05f))
            drawPath(path = ray3, color = Color(0xFFFF9800), style = Stroke(width = width * 0.05f))
        }
    }
}
