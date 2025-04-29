package com.daemon.smusignal

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.daemon.smusignal.data.remote.RetrofitClient
import com.daemon.smusignal.data.remote.TokenManager

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val sharedPrefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        RetrofitClient.init(TokenManager(sharedPrefs))
    }
}