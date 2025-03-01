package com.example.blood_aid.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blood_aid.R
import com.example.blood_aid.databinding.ActivityUserDashBinding
import com.example.blood_aid.repository.UserRepository
import com.example.blood_aid.repository.UserRepositoryImpl
import com.example.blood_aid.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseUser

class UserDashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDashBinding
    private lateinit var userViewModel: UserViewModel
    private val uid= intent.getStringExtra("uid").toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserDashBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.usernameDash.text="Welcome to Blood Aid";

        binding.profileNav.setOnClickListener {
            val intent = Intent(this@UserDashActivity,UserProfileActivity::class.java)
            intent.putExtra("uid",uid)
            startActivity(intent)
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}