package com.example.cegct.features.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cegct.features.auth.ui.components.GreenCurvedHeader
import com.example.cegct.ui.theme.GreenPrimary

data class PhoneContact(
    val id: String,
    val name: String,
    val phone: String,
    val avatarLetter: String
)

@Composable
fun CommunityScreen(
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val invitedContactIds = remember { mutableStateListOf<String>() }

    val contactsList = remember {
        listOf(
            PhoneContact("1", "Chifundo Banda", "+265 991 234 567", "C"),
            PhoneContact("2", "Blessings Phiri", "+265 888 123 456", "B"),
            PhoneContact("3", "Kondwani Mwale", "+265 999 876 543", "K"),
            PhoneContact("4", "Tiyamike Mphepo", "+265 881 112 233", "T"),
            PhoneContact("5", "Zikomo Tembo", "+265 992 554 433", "Z"),
            PhoneContact("6", "Chawanangwa Gondwe", "+265 884 990 011", "C"),
            PhoneContact("7", "Memory Nyirenda", "+265 993 441 122", "M")
        )
    }

    val filteredContacts = contactsList.filter {
        it.name.contains(searchQuery, ignoreCase = true) || it.phone.contains(searchQuery)
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            GreenCurvedHeader(
                title = "Community & Invite",
                subtitle = "Invite citizens from your phone contacts",
                onBack = onBack
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Search Contacts Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search contacts by name or phone...") },
                    leadingIcon = { Text("🔍", modifier = Modifier.padding(start = 8.dp)) },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "PHONE CONTACTS (${filteredContacts.size})",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(10.dp))

                filteredContacts.forEach { contact ->
                    val isInvited = invitedContactIds.contains(contact.id)

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(GreenPrimary.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = contact.avatarLetter,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = GreenPrimary
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = contact.name,
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = contact.phone,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                            }

                            Button(
                                onClick = {
                                    if (isInvited) {
                                        invitedContactIds.remove(contact.id)
                                    } else {
                                        invitedContactIds.add(contact.id)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isInvited) Color(0xFFE8F5E9) else GreenPrimary,
                                    contentColor = if (isInvited) GreenPrimary else Color.White
                                ),
                                shape = RoundedCornerShape(16.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = if (isInvited) "✓ Invited" else "Invite",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
