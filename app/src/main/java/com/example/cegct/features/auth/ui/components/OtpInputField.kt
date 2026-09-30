package com.example.cegct.features.auth.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cegct.ui.theme.GreenPrimary

@Composable
fun OtpInputField(
    codeLength: Int = 6,
    otpValue: String,
    onOtpChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequesters = remember { List(codeLength) { FocusRequester() } }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until codeLength) {
            val char = otpValue.getOrNull(i)?.toString() ?: ""
            val isFocused = otpValue.length == i || (i == codeLength - 1 && otpValue.length == codeLength)

            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(56.dp)
                    .border(
                        width = if (isFocused) 2.dp else 1.dp,
                        color = if (isFocused) GreenPrimary else Color.LightGray,
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    value = char,
                    onValueChange = { newValue ->
                        if (newValue.length > 1) {
                            // Support pasting multiple digits
                            val digitsOnly = newValue.filter { it.isDigit() }
                            if (digitsOnly.length >= codeLength) {
                                onOtpChanged(digitsOnly.take(codeLength))
                                focusRequesters[codeLength - 1].requestFocus()
                            } else {
                                val combined = (otpValue.take(i) + digitsOnly).take(codeLength)
                                onOtpChanged(combined)
                                val nextIndex = (i + digitsOnly.length).coerceAtMost(codeLength - 1)
                                focusRequesters[nextIndex].requestFocus()
                            }
                        } else if (newValue.isNotEmpty() && newValue.all { it.isDigit() }) {
                            // Single digit entered
                            val newOtp = StringBuilder(otpValue).apply {
                                if (i < length) {
                                    setCharAt(i, newValue[0])
                                } else {
                                    append(newValue[0])
                                }
                            }.toString().take(codeLength)
                            onOtpChanged(newOtp)
                            if (i < codeLength - 1) {
                                focusRequesters[i + 1].requestFocus()
                            }
                        } else if (newValue.isEmpty()) {
                            // Empty input / backspace
                            val newOtp = if (i < otpValue.length) {
                                otpValue.removeRange(i, i + 1)
                            } else {
                                otpValue.dropLast(1)
                            }
                            onOtpChanged(newOtp)
                            if (i > 0) {
                                focusRequesters[i - 1].requestFocus()
                            }
                        }
                    },
                    modifier = Modifier
                        .focusRequester(focusRequesters[i])
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Backspace) {
                                if (char.isEmpty() && i > 0) {
                                    focusRequesters[i - 1].requestFocus()
                                    true
                                } else false
                            } else false
                        },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (char.isEmpty()) {
                                Text(
                                    text = "•",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.LightGray
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }
    }
}
