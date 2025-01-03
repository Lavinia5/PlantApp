package com.example.plantapp

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.plantapp.R

// Statistics Activity
class StatisticsActivity : AppCompatActivity() {

    private lateinit var databaseHelper: PlantDatabaseHelper
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        // Inițializează baza de date
        databaseHelper = PlantDatabaseHelper(this)
        db = databaseHelper.readableDatabase

        // Afișează statisticile
        displayStatistics()
    }

    @SuppressLint("Range")
    private fun displayStatistics() {
        val statisticsTextView: TextView = findViewById(R.id.statistics_text_view)
        val statistics = StringBuilder()

        // Interoghează baza de date pentru statistici
        val query = """
            SELECT ${PlantDatabaseHelper.COLUMN_NAME}, COUNT(${PlantDatabaseHelper.COLUMN_NAME}) AS count
            FROM ${PlantDatabaseHelper.TABLE_PLANTS}
            GROUP BY ${PlantDatabaseHelper.COLUMN_NAME}
            ORDER BY count DESC
        """
        val cursor = db.rawQuery(query, null)

        // Construiește textul pentru afișare
        if (cursor.moveToFirst()) {
            do {
                val plantName = cursor.getString(cursor.getColumnIndex(PlantDatabaseHelper.COLUMN_NAME))
                val count = cursor.getInt(cursor.getColumnIndex("count"))
                statistics.append("$plantName: $count searches\n")
            } while (cursor.moveToNext())
        }
        cursor.close()

        // Setează textul în TextView
        statisticsTextView.text = statistics.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Închide baza de date pentru a elibera resursele
        db.close()
    }
}