package com.example.plantapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.plantapp.R
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class UploadPlantImageActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var selectedImageUri: Uri
    private lateinit var imageView: ImageView
    private lateinit var plantDetailsView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_plant_image)

        imageView = findViewById(R.id.plant_image_view)
        plantDetailsView = findViewById(R.id.plant_details_view)

        val selectImageButton: Button = findViewById(R.id.select_image_button)
        val uploadImageButton: Button = findViewById(R.id.upload_image_button)

        // Deschide galeria pentru a selecta o imagine
        selectImageButton.setOnClickListener {
            openImageChooser()
        }

        // Trimite imaginea către API
        uploadImageButton.setOnClickListener {
            uploadImageToAPI()
        }
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data!!
            imageView.setImageURI(selectedImageUri)
        }
    }

    private fun uploadImageToAPI() {
        if (::selectedImageUri.isInitialized) {
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
            val imageData = byteArrayOutputStream.toByteArray()

            Thread {
                try {
                    val url = URL("https://api.plantsinfo.com/upload") // Schimbă cu URL-ul real al API-ului
                    val connection = url.openConnection() as HttpURLConnection
                    connection.doOutput = true
                    connection.requestMethod = "POST"
                    connection.setRequestProperty("Content-Type", "image/jpeg")

                    val outputStream: OutputStream = connection.outputStream
                    outputStream.write(imageData)
                    outputStream.flush()
                    outputStream.close()

                    val responseCode = connection.responseCode
                    val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }

                    runOnUiThread {
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            plantDetailsView.text = "Response: $responseMessage"
                        } else {
                            plantDetailsView.text = "Error: $responseCode"
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        plantDetailsView.text = "Error: ${e.message}"
                    }
                }
            }.start()
        } else {
            plantDetailsView.text = "Please select an image first."
        }
    }
}
