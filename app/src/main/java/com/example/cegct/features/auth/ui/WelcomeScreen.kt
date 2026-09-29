package com.example.cegct.features.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onContinueAnonymously: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "CEGCT Welcome", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onNavigateToLogin, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(onClick = onNavigateToRegister, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Register")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onContinueAnonymously) {
                Text(text = "Continue Anonymously")
            }
        }
    }
}
