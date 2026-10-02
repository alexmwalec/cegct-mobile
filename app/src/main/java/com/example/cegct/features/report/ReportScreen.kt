package com.example.cegct.features.report

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader
import com.example.cegct.ui.theme.GreenPrimary
import kotlinx.coroutines.delay

private val CameraIcon: ImageVector = ImageVector.Builder("Camera", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(GreenPrimary)) {
        moveTo(9f, 2f)
        lineTo(7.17f, 4f)
        lineTo(4f, 4f)
        curveTo(2.9f, 4f, 2f, 4.9f, 2f, 6f)
        lineTo(2f, 18f)
        curveTo(2f, 19.1f, 2.9f, 20f, 4f, 20f)
        lineTo(20f, 20f)
        curveTo(21.1f, 20f, 22f, 19.1f, 22f, 18f)
        lineTo(22f, 6f)
        curveTo(22f, 4.9f, 21.1f, 4f, 20f, 4f)
        lineTo(16.83f, 4f)
        lineTo(15f, 2f)
        close()
        moveTo(12f, 17f)
        curveTo(9.24f, 17f, 7f, 14.76f, 7f, 12f)
        curveTo(7f, 9.24f, 9.24f, 7f, 12f, 7f)
        curveTo(14.76f, 7f, 17f, 9.24f, 17f, 12f)
        curveTo(17f, 14.76f, 14.76f, 17f, 12f, 17f)
        close()
    }
}.build()

private val MicIcon: ImageVector = ImageVector.Builder("Mic", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(GreenPrimary)) {
        moveTo(12f, 14f)
        curveTo(13.66f, 14f, 15f, 12.66f, 15f, 11f)
        lineTo(15f, 5f)
        curveTo(15f, 3.34f, 13.66f, 2f, 12f, 2f)
        curveTo(10.34f, 2f, 9f, 3.34f, 9f, 5f)
        lineTo(9f, 11f)
        curveTo(9f, 12.66f, 10.34f, 14f, 12f, 14f)
        close()
        moveTo(17f, 11f)
        curveTo(17f, 13.76f, 14.76f, 16f, 12f, 16f)
        curveTo(9.24f, 16f, 7f, 13.76f, 7f, 11f)
        lineTo(5f, 11f)
        curveTo(5f, 14.42f, 7.72f, 17.23f, 11f, 17.72f)
        lineTo(11f, 21f)
        lineTo(13f, 21f)
        lineTo(13f, 17.72f)
        curveTo(16.28f, 17.23f, 19f, 14.42f, 19f, 11f)
        close()
    }
}.build()

private val LocationIcon: ImageVector = ImageVector.Builder("LocationPin", 24.dp, 24.dp, 24f, 24f).apply {
    path(fill = SolidColor(GreenPrimary)) {
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

@Composable
fun ReportScreen(
    category: String = "Illegal dumping",
    onSubmitSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("GPS Location Pinpointed (Current Area)") }
    var isImageAttached by remember { mutableStateOf(false) }
    var isSubmitting by remember { mutableStateOf(false) }

    // Audio recording states
    var isRecording by remember { mutableStateOf(false) }
    var recordingSeconds by remember { mutableIntStateOf(0) }
    var hasVoiceNote by remember { mutableStateOf(false) }

    LaunchedEffect(isRecording) {
        if (isRecording) {
            recordingSeconds = 0
            while (isRecording) {
                delay(1000)
                recordingSeconds += 1
            }
        }
    }

    val micButtonBgColor by animateColorAsState(
        targetValue = if (isRecording) Color(0xFFE53935) else Color(0xFFE8F5E9),
        label = "MicBgColor"
    )

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            GreenCurvedHeader(
                title = "Report Issue",
                subtitle = "Category: $category",
                onBack = onBack
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Category Chip Tag
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFE8F5E9),
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Text(
                        text = "Selected Issue: $category",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = GreenPrimary,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Section 1: Image / Photo Upload Box
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (isImageAttached) Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .border(
                            width = 1.5.dp,
                            color = if (isImageAttached) GreenPrimary else Color.LightGray,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable { isImageAttached = !isImageAttached }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(if (isImageAttached) GreenPrimary else Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = CameraIcon,
                                contentDescription = "Camera",
                                tint = if (isImageAttached) Color.White else GreenPrimary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = if (isImageAttached) "Photo Attached (Tap to Change)" else "Tap to Capture Photo or Upload Image",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = if (isImageAttached) GreenPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Section 2: GPS Location Field
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    leadingIcon = { Icon(LocationIcon, contentDescription = "Location") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Section 3: Description Field with Integrated Microphone Audio Recorder
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Type or record audio",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("Describe the issue details, location landmark, severity...") },
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Audio Recording Action Row with Microphone Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = {
                                    if (isRecording) {
                                        isRecording = false
                                        hasVoiceNote = true
                                    } else {
                                        isRecording = true
                                    }
                                },
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(micButtonBgColor)
                            ) {
                                Icon(
                                    imageVector = MicIcon,
                                    contentDescription = "Record Audio Note",
                                    tint = if (isRecording) Color.White else GreenPrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            if (isRecording) {
                                Text(
                                    text = "Recording audio... (${recordingSeconds}s) - Tap mic to stop",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFE53935)
                                    )
                                )
                            } else if (hasVoiceNote) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color(0xFFE8F5E9)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Voice Note Recorded (${recordingSeconds}s)",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = GreenPrimary
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "✕",
                                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                            color = Color.Gray,
                                            modifier = Modifier.clickable {
                                                hasVoiceNote = false
                                                recordingSeconds = 0
                                            }
                                        )
                                    }
                                }
                            } else {
                                Text(
                                    text = "Tap mic to add voice note",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Section 4: Main Submit Button
                Button(
                    onClick = {
                        isSubmitting = true
                        onSubmitSuccess()
                    },
                    enabled = !isSubmitting,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.5.dp
                        )
                    } else {
                        Text(
                            text = "Submit Report",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}
