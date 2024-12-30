package com.example.blood_aid.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blood_aid.R
import com.example.blood_aid.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding  // declaring binder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityStartBinding.inflate(layoutInflater)  //initializing binder
        setContentView(binding.root)// changed this
        binding.user.setOnClickListener{
            val intent=Intent(this@StartActivity,NewRegistrationActivity::class.java)
            startActivity(intent)
        }
        binding.ngoOrg.setOnClickListener{
            val intent=Intent(this@StartActivity,OrgRegistrationActivity::class.java)
            startActivity(intent)
        }
        binding.landingRegister.setOnClickListener{
            val intent=Intent(this@StartActivity,UserActivity::class.java)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}