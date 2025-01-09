package com.example.plantapp.getalarms
import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.plantapp.NotificationReceiver
import java.util.*

@Composable
fun WateringReminderScreen() {
    val context = LocalContext.current

    // Variabile pentru ora și minutul selectate
    var hour by remember { mutableStateOf(0) }
    var minute by remember { mutableStateOf(0) }
    var timeSet by remember { mutableStateOf(false) } // Indicator pentru afișarea orei selectate

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (timeSet) "Reminder set for: ${formatTime(hour, minute)}" else "No reminder set.",
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Buton pentru a deschide TimePickerDialog
        Button(onClick = {
            TimePickerDialog(
                context,
                { _, selectedHour, selectedMinute ->
                    hour = selectedHour
                    minute = selectedMinute
                    timeSet = true
                    setWateringReminder(context, hour, minute) // Setează notificarea
                    Toast.makeText(
                        context,
                        "Reminder set for ${formatTime(hour, minute)}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
            ).show()
        }) {
            Text("Set Watering Reminder")
        }
    }
}

// Funție pentru setarea notificării
@SuppressLint("ScheduleExactAlarm")
fun setWateringReminder(context: Context, hour: Int, minute: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as android.app.AlarmManager
    val intent = android.content.Intent(context, NotificationReceiver::class.java)
    val pendingIntent = android.app.PendingIntent.getBroadcast(
        context,
        1001,
        intent,
        android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
    )

    // Configurarea timpului pentru alarmă
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
    }

    alarmManager.setExactAndAllowWhileIdle(
        android.app.AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}

// Funție pentru a formata ora
fun formatTime(hour: Int, minute: Int): String {
    return String.format("%02d:%02d", hour, minute)
}
