package com.example.cegct.features.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader
import com.example.cegct.ui.theme.GreenPrimary

@Composable
fun ChatScreen(
    onBack: () -> Unit
) {
    var messageText by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            "Officer Banda (NEMA): Hello, we received your report #CEGCT-2024-0042 regarding water contamination in Ngong River.",
            "Citizen: Thank you! The discharge is still active near the old tannery site.",
            "Officer Banda (NEMA): Our field inspection team has been dispatched for water sampling today at 11:00 AM."
        )
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            GreenCurvedHeader(
                title = "Contact Authority",
                subtitle = "Chatting with Assigned Inspector (NEMA)",
                onBack = onBack
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                messages.forEach { msg ->
                    val isOfficer = msg.startsWith("Officer")
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isOfficer) Color(0xFFE8F5E9) else Color(0xFFF5F5F5),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = msg,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = { Text("Type message to officer...") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            messages.add("Citizen: $messageText")
                            messageText = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(52.dp)
                ) {
                    Text("Send", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
