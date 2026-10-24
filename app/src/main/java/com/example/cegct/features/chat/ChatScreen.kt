package com.example.cegct.features.chat

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.sp
import com.example.cegct.core.network.ChatMessageDto
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader

private val DarkGreenHeader = Color(0xFF185835)

@Composable
fun ChatScreen(
    reportId: String = "#CEGCT-2024-0042",
    onBack: () -> Unit = {}
) {
    var messageText by remember { mutableStateOf("") }
    var isSending by remember { mutableStateOf(false) }

    val chatRepository = remember(reportId) { ChatRepository(reportId) }
    val messages by chatRepository.messages.collectAsState()
    val isLoading by chatRepository.isLoading.collectAsState()
    val sendError by chatRepository.sendError.collectAsState()

    val scrollState = rememberScrollState()

    // Auto-scroll to bottom on new messages
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFE5F0EE))
        ) {
            GreenCurvedHeader(
                title = "Report Conversation",
                subtitle = "Case Number: $reportId",
                showNotificationBell = false,
                showBackArrow = true,
                onBack = onBack
            )

            // Case Number Banner
            Surface(
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Case Number:",
                        style = MaterialTheme.typography.labelLarge.copy(color = Color.Gray)
                    )
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = DarkGreenHeader
                    ) {
                        Text(
                            text = reportId,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Error Banner
            sendError?.let { err ->
                Surface(
                    color = Color(0xFFFFEBEE),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = err,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFD32F2F)),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            // Messages Container
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                if (isLoading && messages.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = DarkGreenHeader)
                    }
                } else if (messages.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "No messages yet",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF333333)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Send a message to contact CEGCT official response team regarding case $reportId.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        messages.forEach { msg ->
                            ChatMessageBubble(msg = msg, reportId = reportId)
                        }
                    }
                }
            }

            // Bottom Input Bar
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        placeholder = { Text("Type reply for case $reportId...") },
                        shape = RoundedCornerShape(16.dp),
                        singleLine = false,
                        maxLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkGreenHeader,
                            unfocusedBorderColor = Color(0xFFCCCCCC)
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = {
                            val textToSend = messageText.trim()
                            if (textToSend.isNotBlank() && !isSending) {
                                isSending = true
                                chatRepository.sendMessage(textToSend) { success ->
                                    isSending = false
                                    if (success) {
                                        messageText = ""
                                    }
                                }
                            }
                        },
                        enabled = messageText.isNotBlank() && !isSending,
                        colors = ButtonDefaults.buttonColors(containerColor = DarkGreenHeader),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(50.dp)
                    ) {
                        if (isSending) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Send", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatMessageBubble(
    msg: ChatMessageDto,
    reportId: String
) {
    val isCitizen = msg.isFromCitizen
    val alignment = if (isCitizen) Alignment.End else Alignment.Start
    val bubbleColor = if (isCitizen) Color(0xFFE8F5E9) else Color.White
    val textColor = Color(0xFF111111)
    val roleBadgeColor = if (isCitizen) DarkGreenHeader else Color(0xFF0288D1)
    val roleTitle = if (isCitizen) "Citizen" else "CEGCT Admin / Officer"

    val isAcknowledgment = !isCitizen && (
        msg.effectiveMessage.contains(reportId, ignoreCase = true) ||
        msg.effectiveMessage.contains("acknowledged", ignoreCase = true) ||
        msg.effectiveMessage.contains("received", ignoreCase = true) ||
        msg.effectiveMessage.contains("case number", ignoreCase = true)
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = if (isAcknowledgment) Color(0xFFFFF3E0) else bubbleColor,
            shadowElevation = 2.dp,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = msg.effectiveSenderName,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF111111)
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = roleBadgeColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = msg.effectiveSenderRole.ifBlank { roleTitle },
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = roleBadgeColor,
                                fontSize = 10.sp
                            ),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                if (isAcknowledgment) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFFF9800).copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "OFFICIAL ACKNOWLEDGMENT",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFE65100),
                                fontSize = 9.sp
                            ),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = msg.effectiveMessage,
                    style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
                    color = textColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = msg.effectiveSentAt,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
