package com.example.plantapp

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.plantapp.R

class NotificationReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        // Creează notificarea
        val notification = NotificationCompat.Builder(context, "watering_reminder_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Asigură-te că această resursă există
            .setContentTitle("Watering Reminder")
            .setContentText("It's time to water your plants!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        // Afișează notificarea
        NotificationManagerCompat.from(context).notify(1001, notification)
    }
}
