package com.example.blood_aid.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blood_aid.R
import com.example.blood_aid.databinding.ActivityBloodRepositoryBinding

class BloodRepositoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBloodRepositoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityBloodRepositoryBinding.inflate(layoutInflater)
        binding.backButton.setOnClickListener{
            finish()
        }
        binding.donateButton.setOnClickListener{
            startActivity(Intent(this,BloodDonationActivity::class.java))
        }
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}