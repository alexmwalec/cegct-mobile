package com.example.cegct.features.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cegct.core.firestore.FirestoreRepository
import com.example.cegct.core.firestore.FirestoreRepositoryImpl
import com.example.cegct.core.firestore.models.ReportEntity
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader

private val DarkGreenHeader = Color(0xFF185835)

fun ReportEntity.toMapReportMarker(): MapReportMarker {
    return MapReportMarker(
        id = reportId,
        title = title.ifBlank { category },
        category = category,
        locationName = address.ifBlank { "Lilongwe, Malawi" },
        latitude = if (latitude != 0.0) latitude else -13.9350,
        longitude = if (longitude != 0.0) longitude else 33.7850,
        status = status,
        urgency = urgency,
        distance = "Nearby"
    )
}

@Composable
fun MapScreen(
    onConfirmLocation: () -> Unit = {},
    onSelectReport: (String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedLocationText by remember { mutableStateOf("Lilongwe Area 25, Sector 3") }

    val repository: FirestoreRepository = remember { FirestoreRepositoryImpl() }
    val realReports by repository.observeAllReports().collectAsState(initial = emptyList())

    val currentUserId = com.example.cegct.core.network.NetworkClient.currentUser?.id.orEmpty()
    val currentUserEmail = com.example.cegct.core.network.NetworkClient.currentUser?.email.orEmpty()

    val nearbyReports = remember(realReports, currentUserId, currentUserEmail) {
        realReports.filter { report ->
            (currentUserId.isBlank() || report.userId != currentUserId) &&
            (currentUserEmail.isBlank() || !report.userEmail.equals(currentUserEmail, ignoreCase = true))
        }
    }

    val mapMarkers = remember(nearbyReports) {
        nearbyReports.map { it.toMapReportMarker() }
    }

    val filteredReports = remember(searchQuery, nearbyReports) {
        if (searchQuery.isBlank()) {
            nearbyReports
        } else {
            nearbyReports.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true) ||
                it.address.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE5F0EE))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header Section
            GreenCurvedHeader(
                title = "Nearby Reports Map",
                subtitle = "Interactive Google Map & Environmental Reports",
                showNotificationBell = false,
                showBackArrow = true,
                onBack = onBack
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Search Bar Input
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search reports or locations (e.g. Area 18, Kanengo)...") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = DarkGreenHeader,
                        unfocusedBorderColor = Color(0xFFCCCCCC)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Interactive Map View Widget
                InteractiveMapWidget(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    markers = mapMarkers,
                    selectedAddressText = selectedLocationText,
                    onLocationSelected = { chosen ->
                        selectedLocationText = chosen
                        onConfirmLocation()
                    },
                    onMarkerSelected = { marker ->
                        if (marker.id.isNotBlank()) {
                            onSelectReport(marker.id)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Nearby Reports List Header
                Text(
                    text = "Nearby Environmental Reports",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (filteredReports.isEmpty()) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (searchQuery.isNotBlank()) "No reports matching '$searchQuery'." else "No nearby environmental reports yet.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        filteredReports.forEach { report ->
                            Card(
                                onClick = { onSelectReport(report.reportId) },
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = Color(0xFFE8F5E9)
                                        ) {
                                            Text(
                                                text = report.category.ifBlank { "Environmental" },
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = DarkGreenHeader
                                                ),
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }

                                        val statusColor = when (report.status.uppercase()) {
                                            "RESOLVED" -> Color(0xFF2E7D32)
                                            "IN_PROGRESS" -> Color(0xFFEF6C00)
                                            else -> Color(0xFF00838F)
                                        }
                                        val statusBg = when (report.status.uppercase()) {
                                            "RESOLVED" -> Color(0xFFE8F5E9)
                                            "IN_PROGRESS" -> Color(0xFFFFF3E0)
                                            else -> Color(0xFFE0F7FA)
                                        }

                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = statusBg
                                        ) {
                                            Text(
                                                text = report.status.ifBlank { "SUBMITTED" },
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = statusColor
                                                ),
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = report.title.ifBlank { report.category },
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color(0xFF111111)
                                    )

                                    if (report.address.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.LocationOn,
                                                contentDescription = "Location",
                                                tint = DarkGreenHeader,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = report.address,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color(0xFF666666)
                                            )
                                        }
                                    }

                                    val imageUrl = report.imageUrls.firstOrNull()
                                    if (!imageUrl.isNullOrBlank()) {
                                        Spacer(modifier = Modifier.height(10.dp))
                                        AsyncImage(
                                            model = imageUrl,
                                            contentDescription = "Report Image",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(160.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
