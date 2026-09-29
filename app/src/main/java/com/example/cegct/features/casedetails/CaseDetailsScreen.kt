package com.example.cegct.features.casedetails

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CaseDetailsScreen(
    reportId: String,
    onNavigateToChat: () -> Unit,
    onBack: () -> Unit
) {
    var rating by remember { mutableStateOf(5) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Case Details ($reportId)", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Timeline
            Text(text = "Timeline:", style = MaterialTheme.typography.titleMedium)
            Text(text = "• Submitted on Oct 12\n• Assigned to Officer Banda on Oct 13\n• Inspection in progress", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(onClick = onNavigateToChat, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Chat with Officer")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Rate Resolution ($rating Stars)")
            }

            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = {}) {
                Text(text = "Reopen Case")
            }

            Spacer(modifier = Modifier.height(24.dp))
            TextButton(onClick = onBack) {
                Text(text = "Back")
            }
        }
    }
}
