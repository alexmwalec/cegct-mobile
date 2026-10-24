package com.example.cegct.features.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

data class MapReportMarker(
    val id: String,
    val title: String,
    val category: String,
    val locationName: String,
    val latitude: Double,
    val longitude: Double,
    val status: String,
    val urgency: String,
    val distance: String = "Nearby",
    val dateText: String = "Today"
)

private val DarkGreenHeader = Color(0xFF185835)

@Composable
fun InteractiveMapWidget(
    modifier: Modifier = Modifier,
    markers: List<MapReportMarker> = emptyList(),
    isPickerMode: Boolean = false,
    selectedAddressText: String = "Lilongwe Area 25, Malawi",
    onLocationSelected: (String) -> Unit = {},
    onMarkerSelected: (MapReportMarker) -> Unit = {}
) {
    val defaultCenter = LatLng(-13.9626, 33.7741) // Lilongwe, Malawi
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultCenter, 13f)
    }
    val coroutineScope = rememberCoroutineScope()

    var selectedMarker by remember(markers) { mutableStateOf(markers.firstOrNull()) }
    var selectedCategoryFilter by remember { mutableStateOf("All") }

    val categories = listOf("All", "Illegal dumping", "Water pollution", "Air pollution", "Deforestation")

    val filteredMarkers = remember(selectedCategoryFilter, markers) {
        if (selectedCategoryFilter == "All") markers
        else markers.filter { it.category.equals(selectedCategoryFilter, ignoreCase = true) }
    }

    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = true,
            compassEnabled = true,
            myLocationButtonEnabled = false,
            mapToolbarEnabled = false
        )
    }

    val properties = remember {
        MapProperties(
            mapType = MapType.NORMAL,
            isMyLocationEnabled = false
        )
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFE8ECE9))
    ) {
        // Real Google Map Integration
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings
        ) {
            if (!isPickerMode) {
                filteredMarkers.forEach { marker ->
                    val markerState = rememberMarkerState(
                        key = marker.id,
                        position = LatLng(marker.latitude, marker.longitude)
                    )
                    Marker(
                        state = markerState,
                        title = marker.title,
                        snippet = "${marker.category} • ${marker.status}",
                        onClick = {
                            selectedMarker = marker
                            onMarkerSelected(marker)
                            false
                        }
                    )
                }
            }
        }

        // Top Category Filter Bar (when not in picker mode)
        if (!isPickerMode && markers.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->
                        val isSelected = selectedCategoryFilter == category
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) DarkGreenHeader else Color.White.copy(alpha = 0.95f),
                            shadowElevation = 3.dp,
                            modifier = Modifier.clickable { selectedCategoryFilter = category }
                        ) {
                            Text(
                                text = category,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else Color(0xFF333333)
                                ),
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        // Recenter Floating Button
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 60.dp, end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SmallFloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newCameraPosition(
                                CameraPosition.fromLatLngZoom(defaultCenter, 13f)
                            )
                        )
                    }
                },
                containerColor = Color.White,
                contentColor = DarkGreenHeader,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = "Recenter Map",
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Location Picker Mode (Center Pin Overlay & Bottom Confirmation Bar)
        if (isPickerMode) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.offset(y = (-20).dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = DarkGreenHeader,
                        shadowElevation = 4.dp
                    ) {
                        Text(
                            text = "Set Location Pin",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location Pin",
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(42.dp)
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Selected Location",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = selectedAddressText,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111111)
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { onLocationSelected(selectedAddressText) },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkGreenHeader),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Confirm Location Pin",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        } else {
            // Selected Report Card Preview Overlay (Bottom of Map)
            selectedMarker?.let { marker ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(12.dp)
                        .clickable { onMarkerSelected(marker) },
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(
                                    when (marker.urgency) {
                                        "CRITICAL" -> Color(0xFFFFEBEE)
                                        "HIGH" -> Color(0xFFFFF3E0)
                                        else -> Color(0xFFE8F5E9)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = when (marker.category) {
                                    "Illegal dumping" -> Icons.Default.Delete
                                    "Water pollution" -> Icons.Default.WaterDrop
                                    "Air pollution" -> Icons.Default.Air
                                    "Deforestation" -> Icons.Default.Park
                                    else -> Icons.Default.Warning
                                },
                                contentDescription = marker.category,
                                tint = when (marker.urgency) {
                                    "CRITICAL" -> Color(0xFFD32F2F)
                                    "HIGH" -> Color(0xFFE65100)
                                    else -> DarkGreenHeader
                                },
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = marker.title,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF111111)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = marker.locationName,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                color = Color(0xFF666666)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFE8F3EE)
                        ) {
                            Text(
                                text = marker.status,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = DarkGreenHeader
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
