package com.example.plantapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inițializează FirebaseAuth
        auth = FirebaseAuth.getInstance()
    }

    // Funcție pentru logare
    fun logIn(view: View) {
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)

        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email și parola sunt necesare", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Autentificare reușită!", Toast.LENGTH_SHORT).show()
                    // Navighează la pagina Home
                    goToHome(view)
                } else {
                    Toast.makeText(this, "Autentificare eșuată: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Funcție pentru înregistrare (sign-up)
    fun signUp(view: View) {
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)

        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email și parola sunt necesare", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Trimitere e-mail de confirmare
                    auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { emailTask ->
                        if (emailTask.isSuccessful) {
                            Toast.makeText(this, "Cont creat. Verificați e-mailul pentru confirmare!", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Eroare la trimiterea e-mailului: ${emailTask.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Înregistrare eșuată: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun goToHome(view: View) {
        // Navighează la HomeActivity
    }
}
