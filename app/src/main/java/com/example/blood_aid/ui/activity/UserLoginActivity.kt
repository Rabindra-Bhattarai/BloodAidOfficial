package com.example.blood_aid.ui.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.blood_aid.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class UserLoginActivity : AppCompatActivity() {

    private lateinit var donorIdInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var signInButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        donorIdInput = findViewById(R.id.donorIdInput)
        passwordInput = findViewById(R.id.passwordInput)
        signInButton = findViewById(R.id.signInButton)

        signInButton.setOnClickListener {
            validateAndLogin()
        }
    }

    private fun validateAndLogin() {
        val email = donorIdInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (email.isEmpty()) {
            donorIdInput.error = "Email is required"
            donorIdInput.requestFocus()
            return
        }

        if (password.isEmpty()) {
            passwordInput.error = "Password is required"
            passwordInput.requestFocus()
            return
        }

        loginUser(email, password)
    }

    private fun loginUser(email: String, password: String) {
        // Dummy authentication logic (replace with real authentication)
        if (email == "test@example.com" && password == "password") {
            // Save login state
            val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("email", email)
            editor.putBoolean("isLoggedIn", true)
            editor.apply()

            // Redirect to next activity
            Toast.makeText(this, "LoggedIn", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
        }
    }
}
