package com.example.cegct.features.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToReport: () -> Unit,
    onNavigateToMyReports: () -> Unit,
    onNavigateToMap: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("Pothole") }
    val categories = listOf("Pothole", "Water Leak", "Trash", "Streetlight")
    var expanded by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "CEGCT Dashboard", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(24.dp))

            // Problem-type dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Problem Type") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable).fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onNavigateToReport, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Report Issue ($selectedCategory)")
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(onClick = onNavigateToMyReports, modifier = Modifier.fillMaxWidth()) {
                Text(text = "View My Reports")
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(onClick = onNavigateToMap, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Nearby Reports Map")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onNavigateToProfile) {
                Text(text = "Profile & Settings")
            }
        }
    }
}
