package com.daemon.smusignal.network

import android.content.SharedPreferences

class TokenManager(private val sharedPreferences: SharedPreferences) {
    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
    }

    fun saveTokens(accessToken: String) {
        sharedPreferences.edit().apply {
            putString(ACCESS_TOKEN, accessToken)
            apply()
        }
    }

    fun getAccessToken(): String? = sharedPreferences.getString(ACCESS_TOKEN, null)
    fun clearTokens() {
        sharedPreferences.edit().clear().apply()
    }
}