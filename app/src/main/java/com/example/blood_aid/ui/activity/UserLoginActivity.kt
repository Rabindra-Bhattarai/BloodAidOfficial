package com.example.blood_aid.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.blood_aid.R
import com.example.blood_aid.repository.UserRepositoryImpl
import com.example.blood_aid.viewmodel.UserViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.example.blood_aid.ui.activity.OrganizationDashActivity
import com.example.blood_aid.viewmodel.IndividualViewModel

class UserLoginActivity : AppCompatActivity() {

    private lateinit var donorIdInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var signInButton: MaterialButton
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        donorIdInput = findViewById(R.id.donorIdInput)
        passwordInput = findViewById(R.id.passwordInput)
        signInButton = findViewById(R.id.signInButton)

        userViewModel = UserViewModel(UserRepositoryImpl())
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
        userViewModel.login(email, password) { success, message ->
            if (success) {
                Toast.makeText(this@UserLoginActivity, message, Toast.LENGTH_SHORT).show()

                val userId = userViewModel.getCurrentUser()?.uid.toString()

                userViewModel.getDataFromDB(userId) { data, dataSuccess, dataMessage ->
                    if (dataSuccess) {
                        Toast.makeText(this@UserLoginActivity, dataMessage, Toast.LENGTH_SHORT).show()

                        when (data.userType) {
                            "ADMN" -> {
                                val intent = Intent(this@UserLoginActivity, AdminDashActivity::class.java)
                                startActivity(intent)
                            }
                            "ORG" -> {
                                val intent = Intent(this@UserLoginActivity, OrganizationDashActivity::class.java)
                                startActivity(intent)
                            }
                            "IND" -> {
                                val intent = Intent(this@UserLoginActivity, UserDashActivity::class.java)
                                startActivity(intent)
                            }
                            else -> {
                                Toast.makeText(this@UserLoginActivity, "Unknown user type", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@UserLoginActivity, dataMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }



}
