package com.example.cegct.features.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader

private val DarkGreenHeader = Color(0xFF185835)

@Composable
fun MapScreen(
    onConfirmLocation: () -> Unit,
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedLocationText by remember { mutableStateOf("Lilongwe Area 25, Sector 3") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE5F0EE))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Section
            GreenCurvedHeader(
                title = "Nearby Reports Map",
                subtitle = "Interactive Google Map & Environmental Heatmap",
                showNotificationBell = false,
                showBackArrow = true,
                onBack = onBack
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Search Bar Input
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search location in Malawi (e.g. Area 18, Kanengo)...") },
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
                        .weight(1f),
                    selectedAddressText = selectedLocationText,
                    onLocationSelected = { chosen ->
                        selectedLocationText = chosen
                        onConfirmLocation()
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Bottom Action Button
                Button(
                    onClick = onConfirmLocation,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGreenHeader),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Confirm Selected Location Pin",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
