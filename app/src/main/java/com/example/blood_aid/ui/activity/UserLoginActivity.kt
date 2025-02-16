package com.example.blood_aid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.blood_aid.R
import com.example.blood_aid.repository.UserRepositoryImpl
import com.example.blood_aid.utils.LocalStorage
import com.example.blood_aid.viewmodel.UserViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class UserLoginActivity : AppCompatActivity() {

    private lateinit var donorIdInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var signInButton: MaterialButton
    private lateinit var userViewModel: UserViewModel
    private lateinit var userId: String
    private lateinit var forgetPassword: TextView
    private lateinit var registerlink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        LocalStorage.init(this)
        donorIdInput = findViewById(R.id.donorIdInput)
        passwordInput = findViewById(R.id.passwordInput)
        signInButton = findViewById(R.id.signInButton)
        forgetPassword= findViewById(R.id.forgotPasswordLink)
        registerlink= findViewById(R.id.registerLink)

        forgetPassword.setOnClickListener{
            startActivity(Intent(this@UserLoginActivity,ForgotPasswordActivity::class.java))
        }
        registerlink.setOnClickListener{
            startActivity(Intent(this@UserLoginActivity,StartActivity::class.java))
        }

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
                userId = userViewModel.getCurrentUser()?.uid.toString()
                Toast.makeText(this@UserLoginActivity, "Logged in", Toast.LENGTH_SHORT).show()

                // Store user credentials using LocalStorage
                LocalStorage.storeUserCredentials( email,password)

                    userViewModel.getDataFromDB(userViewModel.getCurrentUser()?.uid.toString()){
                    type->
                    if (type=="IND"){
                        Toast.makeText(this@UserLoginActivity, "Indi", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@UserLoginActivity, UserDashActivity::class.java)
                        intent.putExtra("ID", userId)
                        startActivity(intent)
                        finish()

                    }else
                    if (type=="ORG"){
                        Toast.makeText(this@UserLoginActivity, "ORG", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@UserLoginActivity, OrganizationDashActivity::class.java)
                        intent.putExtra("ID", userId)
                        startActivity(intent)
                        finish()
                    }else
                    if (type=="ADMN"){
                        Toast.makeText(this@UserLoginActivity, "admn", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@UserLoginActivity, AdminDashActivity::class.java)
                        intent.putExtra("ID", userId)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this@UserLoginActivity, "NETWORK SECURITY EVENT DETECTED", Toast.LENGTH_SHORT).show()
                    }
                }
        }else{
                Toast.makeText(this@UserLoginActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
