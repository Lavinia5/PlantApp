package com.example.plantapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Initialize Facebook SDK
        FacebookSdk.setClientToken(BuildConfig.FACEBOOK_CLIENT_TOKEN)
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "watering_reminder_channel",
                "Watering Reminder",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for watering plants"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
    }
