package com.example.blood_aid.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
                Toast.makeText(this@UserLoginActivity,message,Toast.LENGTH_SHORT).show()
                val userid= userViewModel.getCurrentUser()?.uid.toString()
                userViewModel.getDataFromDB(userid){
                        data,succes,messagee->
                    if(succes){
                        Toast.makeText(this@UserLoginActivity,messagee,Toast.LENGTH_SHORT).show()
                        if(data.userType == "AMDN"){
                            val intent=Intent(this@UserLoginActivity,AdminDashActivity::class.java)
                            startActivity(intent)
                        }
                        if(data.userType == "ORG"){
                            val intent=Intent(this@UserLoginActivity,OrganizationDashActivity::class.java)
                            startActivity(intent)
                        }
                        if(data.userType == "IND"){
                            val intent=Intent(this@UserLoginActivity,UserDashActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    else{
                        Toast.makeText(this@UserLoginActivity,messagee,Toast.LENGTH_SHORT).show()
                    }

                }
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
