package com.example.cegct.features.notifications

import android.util.Log

class FcmService {
    fun onNewToken(token: String) {
        Log.d("FcmService", "New FCM token: $token")
    }

    fun onMessageReceived(title: String, body: String) {
        Log.d("FcmService", "Message received: $title - $body")
    }
}
