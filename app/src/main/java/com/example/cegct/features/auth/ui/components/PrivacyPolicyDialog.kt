package com.example.cegct.features.auth.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cegct.features.auth.ui.AuthStrings

@Composable
fun PrivacyPolicyDialog(
    strings: AuthStrings,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = strings.privacyPolicy,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 350.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Reporter Identity Protection Policy",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "1. Anonymous Reporting Support:\nYou have full control over your reporting identity. Reports can be submitted anonymously without linking your personal details.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "2. Data Encryption & Security:\nAll personal information, phone numbers, and location telemetry submitted via CEGCT are encrypted in transit and at rest.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "3. Zero Third-Party Sharing:\nYour identity and contact details will never be sold, rented, or disclosed to unauthorized third parties or public forums.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "4. Whistleblower & Citizen Safety:\nProtection of citizens submitting community infrastructure and governance reports is a fundamental priority under CEGCT pilot safety guidelines.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
