package com.example.blood_aid.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.blood_aid.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var submitButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var backbutton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Bind views
        emailInput = findViewById(R.id.orgtEmail)
        submitButton = findViewById(R.id.btnOk)
        backbutton=findViewById(R.id.btnCancel)

        backbutton.setOnClickListener{
            finish()
        }

        // Set click listener for the submit button
        submitButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else {
                sendPasswordResetEmail(email)
            }
        }
    }

    private fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
