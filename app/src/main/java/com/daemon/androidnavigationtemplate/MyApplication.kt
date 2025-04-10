package com.daemon.androidnavigationtemplate

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}