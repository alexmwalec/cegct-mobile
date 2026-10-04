package com.example.cegct.features.report

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val DarkGreenHeader = Color(0xFF185835)
private val ScreenBackground = Color(0xFFE5F0EE)
private val LabelTextColor = Color(0xFF1A1A1A)

private val BackArrowIcon: ImageVector = ImageVector.Builder(
    name = "BackArrow",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = null, stroke = SolidColor(Color.White), strokeLineWidth = 2.5f) {
        moveTo(19f, 12f)
        lineTo(5f, 12f)
        moveTo(11f, 18f)
        lineTo(5f, 12f)
        lineTo(11f, 6f)
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

private val CameraIcon: ImageVector = ImageVector.Builder(
    name = "Camera",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(Color(0xFF444444))) {
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

private val MicIcon: ImageVector = ImageVector.Builder(
    name = "Mic",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(Color(0xFF444444))) {
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

private val LocationIcon: ImageVector = ImageVector.Builder(
    name = "LocationPin",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(Color(0xFF222222))) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    category: String = "Illegal dumping",
    onSubmitSuccess: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    var selectedCategory by remember { mutableStateOf(category) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    var locationAddress by remember { mutableStateOf("Luwinga, Mzuzu") }
    var isImageAttached by remember { mutableStateOf(false) }
    var isSubmitting by remember { mutableStateOf(false) }

    // Audio recording states
    var isRecording by remember { mutableStateOf(false) }
    var recordingSeconds by remember { mutableIntStateOf(0) }
    var hasVoiceNote by remember { mutableStateOf(false) }

    val categoriesList = listOf(
        "Illegal dumping",
        "Air pollution",
        "Unsafe waste disposal",
        "Water pollution",
        "Other"
    )

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
        targetValue = if (isRecording) Color(0xFFE53935) else Color(0xFFE5E5E5),
        label = "MicBgColor"
    )

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
            // Header Section
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
                    // Top Bar Row: Back, CEGCT title, Notification Bell
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = BackArrowIcon,
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable { onBack() }
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = "CEGCT",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                ),
                                color = Color.White
                            )
                        }

                        // Notification Bell Icon inside light circle
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

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Empowering you to heal the Planet",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Report Issue",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        ),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // 1. Upload Image Box (Camera Icon)
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (isImageAttached) Color(0xFFE8F5E9) else Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clickable { isImageAttached = !isImageAttached }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(if (isImageAttached) DarkGreenHeader else Color(0xFFE5E5E5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = CameraIcon,
                                contentDescription = "Camera",
                                tint = if (isImageAttached) Color.White else Color(0xFF444444),
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (isImageAttached) "Photo Attached" else "Upload Image",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            color = LabelTextColor
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Capture evidence of the incident (JPG,PNG,MP4)",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = Color(0xFF757575)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 2. Dropdown for Choosing Issue Type (Old style)
                ExposedDropdownMenuBox(
                    expanded = dropdownExpanded,
                    onExpandedChange = { dropdownExpanded = !dropdownExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Choose Issue Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color(0xFF757575),
                            unfocusedBorderColor = Color(0xFF757575),
                            focusedLabelColor = Color(0xFF555555),
                            unfocusedLabelColor = Color(0xFF555555)
                        ),
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        categoriesList.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (item == selectedCategory) FontWeight.Bold else FontWeight.Normal,
                                            color = if (item == selectedCategory) DarkGreenHeader else LabelTextColor
                                        )
                                    )
                                },
                                onClick = {
                                    selectedCategory = item
                                    dropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 3. Description of Issue Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Description of Issue",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                ),
                                color = LabelTextColor
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            TextField(
                                value = description,
                                onValueChange = { if (it.length <= 500) description = it },
                                placeholder = {
                                    Text(
                                        text = "",
                                        color = Color(0xFF888888)
                                    )
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    focusedTextColor = LabelTextColor,
                                    unfocusedTextColor = LabelTextColor
                                ),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                            )
                        }

                        // Mic Button at Bottom Right
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(micButtonBgColor)
                                .clickable {
                                    if (isRecording) {
                                        isRecording = false
                                        hasVoiceNote = true
                                    } else {
                                        isRecording = true
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = MicIcon,
                                contentDescription = "Record Voice Note",
                                tint = if (isRecording) Color.White else Color(0xFF444444),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 4. GPS Pinpoint Location Card (Location Pin Icon - Compact Size)
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = LocationIcon,
                            contentDescription = "Location",
                            tint = DarkGreenHeader,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        TextField(
                            value = locationAddress,
                            onValueChange = { locationAddress = it },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedTextColor = LabelTextColor,
                                unfocusedTextColor = LabelTextColor
                            ),
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // 5. Submit Report Button
                Button(
                    onClick = {
                        isSubmitting = true
                        onSubmitSuccess()
                    },
                    enabled = !isSubmitting,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreenHeader,
                        contentColor = Color.White,
                        disabledContainerColor = DarkGreenHeader.copy(alpha = 0.6f),
                        disabledContentColor = Color.White.copy(alpha = 0.8f)
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
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
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
fun ReportScreenPreview() {
    ReportScreen()
}
