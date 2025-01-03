package com.example.plantapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.plantapp.R
import java.net.HttpURLConnection
import java.net.URL

class EnterPlantNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_plant_name)

        val plantNameInput: EditText = findViewById(R.id.plant_name_input)
        val getDetailsButton: Button = findViewById(R.id.get_details_button)
        val plantDetailsView: TextView = findViewById(R.id.plant_details_view)

        getDetailsButton.setOnClickListener {
            val plantName = plantNameInput.text.toString()
            if (plantName.isNotEmpty()) {
                getPlantDetailsFromAPI(plantName) { details ->
                    runOnUiThread {
                        plantDetailsView.text = details
                    }
                }
            } else {
                plantDetailsView.text = "Please enter a plant name."
            }
        }
    }

    private fun getPlantDetailsFromAPI(plantName: String, callback: (String) -> Unit) {
        Thread {
            try {
                val url = URL("https://api.plantsinfo.com/details?name=$plantName")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val response = inputStream.bufferedReader().use { it.readText() }
                    callback(response)
                } else {
                    callback("Error: Unable to fetch details. Response code: $responseCode")
                }
                connection.disconnect()
            } catch (e: Exception) {
                callback("Failed to fetch details: ${e.message}")
            }
        }.start()
    }
}
