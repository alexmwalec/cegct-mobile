package com.example.cegct.features.report

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader
import com.example.cegct.features.map.InteractiveMapWidget
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

private val DarkGreenHeader = Color(0xFF185835)
private val ScreenBackground = Color(0xFFE5F0EE)
private val LabelTextColor = Color(0xFF111111)

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
    path(fill = SolidColor(DarkGreenHeader)) {
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
    onNavigateToNotifications: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val repository: com.example.cegct.core.firestore.FirestoreRepository = remember { com.example.cegct.core.firestore.FirestoreRepositoryImpl() }
    var selectedCategory by remember { mutableStateOf(category) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    var locationAddress by remember { mutableStateOf("Area 25 Sector 3, Lilongwe") }
    var isSubmitting by remember { mutableStateOf(false) }
    var submissionError by remember { mutableStateOf<String?>(null) }
    var isRecording by remember { mutableStateOf(false) }
    var recordingSeconds by remember { mutableIntStateOf(0) }
    var hasVoiceNote by remember { mutableStateOf(false) }

    // Upload Image state & Activity Result Launchers
    val attachedImageUris = remember { mutableStateListOf<Uri>() }
    var showImagePickerDialog by remember { mutableStateOf(false) }
    var showMapPickerSheet by remember { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            try {
                val file = File(context.cacheDir, "report_photo_${System.currentTimeMillis()}.jpg")
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                }
                attachedImageUris.add(Uri.fromFile(file))
            } catch (_: Exception) {
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            attachedImageUris.addAll(uris)
        }
    }

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

    // Image Picker Option Dialog
    if (showImagePickerDialog) {
        AlertDialog(
            onDismissRequest = { showImagePickerDialog = false },
            title = {
                Text(
                    text = "Upload Evidence Photo",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Text("Select an option to upload or take photo evidence for this incident:")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showImagePickerDialog = false
                        cameraLauncher.launch(null)
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = "Take Photo",
                            tint = DarkGreenHeader,
                            modifier = Modifier.size(18.dp)
                        )
                        Text("Take Photo", color = DarkGreenHeader, fontWeight = FontWeight.Bold)
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showImagePickerDialog = false
                        galleryLauncher.launch("image/*")
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoLibrary,
                            contentDescription = "Choose from Gallery",
                            tint = DarkGreenHeader,
                            modifier = Modifier.size(18.dp)
                        )
                        Text("Choose from Gallery", color = DarkGreenHeader)
                    }
                }
            }
        )
    }

    // Google Map Location Picker BottomSheet
    if (showMapPickerSheet) {
        ModalBottomSheet(
            onDismissRequest = { showMapPickerSheet = false },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(560.dp)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = "Location Pin",
                                tint = DarkGreenHeader,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Choose Location on Google Map",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                ),
                                color = DarkGreenHeader
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Drag or tap on map to pick exact location pin",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF666666))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                InteractiveMapWidget(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    isPickerMode = true,
                    selectedAddressText = locationAddress.ifBlank { "Area 25 Sector 3, Lilongwe" },
                    onLocationSelected = { chosenAddress ->
                        locationAddress = chosenAddress
                        showMapPickerSheet = false
                    }
                )
            }
        }
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
                .imePadding()
        ) {
            // Header Section
            GreenCurvedHeader(
                title = "Report Issue",
                subtitle = "Empowering you to heal the Planet",
                showNotificationBell = true,
                showBackArrow = true,
                onNotificationClick = onNavigateToNotifications,
                onBack = onBack
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // 1. Upload Image Card (Camera / Gallery Picker)
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E5E5)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 160.dp)
                        .clickable { showImagePickerDialog = true }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (attachedImageUris.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE8F3EE)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = CameraIcon,
                                    contentDescription = "Camera",
                                    tint = DarkGreenHeader,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Upload Image / Take Photo",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                ),
                                color = LabelTextColor
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Tap to open camera or choose evidence photos from gallery",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                color = Color(0xFF666666)
                            )
                        } else {
                            // Thumbnails of Attached Images
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Attached Photos (${attachedImageUris.size}) ✓",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = DarkGreenHeader
                                    )
                                )

                                TextButton(onClick = { showImagePickerDialog = true }) {
                                    Text(
                                        text = "+ Add More",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = DarkGreenHeader
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(attachedImageUris) { uri ->
                                    UriThumbnailItem(
                                        uri = uri,
                                        onRemove = { attachedImageUris.remove(uri) }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 2. Dropdown for Choosing Issue Type
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
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = DarkGreenHeader,
                            unfocusedBorderColor = Color(0xFFCCCCCC),
                            focusedLabelColor = DarkGreenHeader,
                            unfocusedLabelColor = Color(0xFF555555),
                            focusedTextColor = LabelTextColor,
                            unfocusedTextColor = LabelTextColor
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

                Spacer(modifier = Modifier.height(16.dp))

                // 3. Description of Issue Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E5E5)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 160.dp)
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
                            Text(
                                text = "Description of Issue",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                ),
                                color = LabelTextColor
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Mic Record Button cleanly positioned in header row
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = if (isRecording) Color(0xFFD32F2F) else if (hasVoiceNote) Color(0xFFE8F5E9) else Color(0xFFF0F0F0),
                                    modifier = Modifier.clickable {
                                        if (isRecording) {
                                            isRecording = false
                                            hasVoiceNote = true
                                        } else {
                                            isRecording = true
                                        }
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = MicIcon,
                                            contentDescription = "Record Voice Note",
                                            tint = if (isRecording) Color.White else if (hasVoiceNote) DarkGreenHeader else Color(0xFF444444),
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (isRecording) "Rec 00:${if (recordingSeconds < 10) "0" else ""}$recordingSeconds"
                                                   else if (hasVoiceNote) "Voice Note ✓"
                                                   else "Record",
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = if (isRecording) Color.White else if (hasVoiceNote) DarkGreenHeader else Color(0xFF444444)
                                        )
                                    }
                                }

                                Text(
                                    text = "${description.length}/500",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                    color = Color(0xFF888888)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 100.dp)
                        ) {
                            if (description.isEmpty()) {
                                Text(
                                    text = "Type report details or tap 'Record' above to speak...",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = 14.sp,
                                        color = Color(0xFF888888)
                                    )
                                )
                            }

                            BasicTextField(
                                value = description,
                                onValueChange = { if (it.length <= 500) description = it },
                                textStyle = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF111111)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 100.dp, max = 200.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 4. GPS Pinpoint Location Card with Integrated Google Map Picker
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E5E5)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE8F3EE))
                                    .clickable { showMapPickerSheet = true },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = LocationIcon,
                                    contentDescription = "Choose Location on Map",
                                    tint = DarkGreenHeader,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "LOCATION ADDRESS",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 0.5.sp
                                        ),
                                        color = DarkGreenHeader
                                    )
                                    Text(
                                        text = " ",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = DarkGreenHeader.copy(alpha = 0.7f)
                                        ),
                                        modifier = Modifier.clickable { showMapPickerSheet = true }
                                    )
                                }

                                Spacer(modifier = Modifier.height(3.dp))

                                BasicTextField(
                                    value = locationAddress,
                                    onValueChange = { locationAddress = it },
                                    singleLine = true,
                                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF111111)
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                if (submissionError != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFEF5350)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = submissionError!!,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFFC62828),
                                    fontWeight = FontWeight.Medium
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { submissionError = null },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Text("✕", color = Color(0xFFC62828), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // 5. Submit Report Button
                Button(
                    onClick = {
                        isSubmitting = true
                        submissionError = null
                        coroutineScope.launch {
                            val selectedUri = attachedImageUris.firstOrNull()
                            val curUser = com.example.cegct.core.network.NetworkClient.currentUser
                            val newReport = com.example.cegct.core.firestore.models.ReportEntity(
                                userId = curUser?.id ?: "",
                                userName = curUser?.name ?: "Citizen",
                                userEmail = curUser?.email ?: "",
                                category = selectedCategory,
                                title = selectedCategory,
                                description = description,
                                address = locationAddress.ifBlank { "Area 25 Sector 3, Lilongwe" },
                                latitude = -13.9350,
                                longitude = 33.7850
                            )
                            val result = repository.createReport(
                                report = newReport,
                                imageUri = selectedUri,
                                context = context
                            )
                            isSubmitting = false
                            if (result.isSuccess) {
                                onSubmitSuccess()
                            } else {
                                submissionError = result.exceptionOrNull()?.message ?: "Report submission failed."
                            }
                        }
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

@Composable
fun UriThumbnailItem(
    uri: Uri,
    onRemove: () -> Unit
) {
    val context = LocalContext.current
    val bitmap = remember(uri) {
        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }?.asImageBitmap()
        } catch (_: Exception) {
            null
        }
    }

    Box(
        modifier = Modifier
            .size(90.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFEEEEEE))
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap,
                contentDescription = "Uploaded evidence image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = CameraIcon, contentDescription = null, tint = Color.Gray)
            }
        }

        // Delete button (X)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .size(22.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.6f))
                .clickable { onRemove() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "✕", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportScreenPreview() {
    ReportScreen()
}
