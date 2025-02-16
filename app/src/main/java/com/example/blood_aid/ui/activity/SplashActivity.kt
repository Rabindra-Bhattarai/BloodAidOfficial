package com.example.blood_aid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import com.example.blood_aid.R
import com.example.blood_aid.repository.UserRepositoryImpl
import com.example.blood_aid.utils.LocalStorage
import com.example.blood_aid.viewmodel.UserViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        LocalStorage.init(this)
        userViewModel = UserViewModel(UserRepositoryImpl())

        Handler(Looper.getMainLooper()).postDelayed({
            checkStoredCredentials()
        }, 3000)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun checkStoredCredentials() {
        val (email, password) = LocalStorage.fetchUserCredentials()

        if (email != null && password != null) {
            // Attempt automatic login
            userViewModel.login(email, password) { success, message ->
                if (success) {
                    userViewModel.getDataFromDB(userViewModel.getCurrentUser()?.uid.toString()) { type ->
                        when (type) {
                            "IND" -> {
                                val intent = Intent(this@SplashActivity, UserDashActivity::class.java)
                                intent.putExtra("EMAIL", email)
                                startActivity(intent)
                                finish()
                            }
                            "ORG" -> {
                                val intent = Intent(this@SplashActivity, OrganizationDashActivity::class.java)
                                intent.putExtra("EMAIL", email)
                                startActivity(intent)
                                finish()
                            }
                            "ADMN" -> {
                                val intent = Intent(this@SplashActivity, AdminDashActivity::class.java)
                                intent.putExtra("EMAIL", email)
                                startActivity(intent)
                                finish()
                            }
                            else -> {
                                Toast.makeText(this@SplashActivity, "NETWORK SECURITY EVENT DETECTED", Toast.LENGTH_SHORT).show()
                                redirectToStartActivity()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this@SplashActivity, message, Toast.LENGTH_SHORT).show()
                    redirectToStartActivity()
                }
            }
        } else {
            // No stored credentials, redirect to StartActivity
            redirectToStartActivity()
        }
    }

    private fun redirectToStartActivity() {
        val intent = Intent(this@SplashActivity, StartActivity::class.java)
        startActivity(intent)
        finish()
    }
}
