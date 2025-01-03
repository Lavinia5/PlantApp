package com.example.plantapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.plantapp.R

class NewAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newaccount)

        val nameEditText = findViewById<EditText>(R.id.editTextName)
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val confirmPasswordEditText = findViewById<EditText>(R.id.editTextConfirmPassword)
        val sendEmailButton = findViewById<Button>(R.id.buttonSendEmail)

        sendEmailButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (validateInputs(name, email, password, confirmPassword)) {
                sendEmailConfirmation(email)
            }
        }
        fun goBack(view: View) {
            onBackPressed()
        }
    }

    private fun validateInputs(name: String, email: String, password: String, confirmPassword: String): Boolean {
        if (name.isEmpty()) {
            showToast("Please enter your name")
            return false
        }
        if (email.isEmpty()) {
            showToast("Please enter your email")
            return false
        }
        if (password.isEmpty()) {
            showToast("Please enter your password")
            return false
        }
        if (confirmPassword.isEmpty()) {
            showToast("Please confirm your password")
            return false
        }
        if (password != confirmPassword) {
            showToast("Passwords do not match")
            return false
        }
        return true
    }

    // Funcția pentru trimiterea unui email de confirmare (deocamdată simulează doar)
    private fun sendEmailConfirmation(email: String) {
        showToast("Email confirmation sent to $email")
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
