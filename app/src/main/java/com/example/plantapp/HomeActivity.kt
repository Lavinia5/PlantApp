package com.example.plantapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.plantapp.R


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val enterNameButton: Button = findViewById(R.id.enter_name_button)
        val uploadImageButton: Button = findViewById(R.id.upload_image_button)
        val statisticsButton: Button = findViewById(R.id.statistics_button)

        // Navigare către activitatea pentru introducerea numelui plantei
        enterNameButton.setOnClickListener {
            val intent = Intent(this, EnterPlantNameActivity::class.java)
            startActivity(intent)
        }

        // Navigare către activitatea pentru încărcarea unei imagini
        uploadImageButton.setOnClickListener {
            val intent = Intent(this, UploadPlantImageActivity::class.java)
            startActivity(intent)
        }

        // Navigare către ecranul de statistici
        statisticsButton.setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }
    }

    fun goBack(view: View) {
        onBackPressed()
    }
}
