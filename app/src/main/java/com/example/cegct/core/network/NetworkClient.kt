package com.example.cegct.core.network

import android.content.Context
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkClient {

    // Production Render Backend Configurations
    private const val PROD_BASE_URL = "https://cegct-api.onrender.com/api/"
    private const val PROD_SOCKET_URL = "https://cegct-api.onrender.com"

    // Local Development Configurations (For local emulator)
    private const val DEV_BASE_URL = "http://192.168.1.134:5001/api/"
    private const val DEV_SOCKET_URL = "http://192.168.1.134:5001"

    // Active Production Endpoints
    const val BASE_URL = PROD_BASE_URL
    const val SOCKET_URL = PROD_SOCKET_URL

    private const val PREFS_NAME = "cegct_user_prefs"
    private const val KEY_TOKEN = "jwt_token"
    private const val KEY_USER = "user_json"

    var authToken: String? = null
    var currentUser: UserDto? = null

    private val okHttpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    val apiService: CegctApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CegctApiService::class.java)
    }

    private var socket: Socket? = null

    fun saveUserSession(context: Context, token: String, user: UserDto) {
        authToken = token
        currentUser = user
        try {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit()
                .putString(KEY_TOKEN, token)
                .putString(KEY_USER, Gson().toJson(user))
                .apply()
        } catch (_: Exception) {}
        connectSocket(token)
    }

    fun loadUserSession(context: Context) {
        try {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val savedToken = prefs.getString(KEY_TOKEN, null)
            val savedUserJson = prefs.getString(KEY_USER, null)
            if (!savedToken.isNullOrEmpty()) {
                authToken = savedToken
                if (!savedUserJson.isNullOrEmpty()) {
                    currentUser = Gson().fromJson(savedUserJson, UserDto::class.java)
                }
                connectSocket(savedToken)
            }
        } catch (_: Exception) {}
    }

    fun clearUserSession(context: Context) {
        authToken = null
        currentUser = null
        disconnectSocket()
        try {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit().clear().apply()
        } catch (_: Exception) {}
    }

    fun connectSocket(token: String): Socket {
        if (socket == null || !socket!!.connected()) {
            val options = IO.Options().apply {
                auth = mapOf("token" to token)
                reconnection = true
                reconnectionAttempts = 10
                reconnectionDelay = 2000
            }
            socket = IO.socket(SOCKET_URL, options)
            socket?.connect()
        }
        return socket!!
    }

    fun disconnectSocket() {
        socket?.disconnect()
        socket = null
    }

    fun getBearerToken(): String {
        val token = authToken ?: ""
        return if (token.isNotBlank()) {
            if (token.startsWith("Bearer ")) token else "Bearer $token"
        } else {
            ""
        }
    }

    fun joinReportRoom(reportId: String) {
        try {
            socket?.emit("join_report_room", reportId)
        } catch (_: Exception) {}
    }
}
