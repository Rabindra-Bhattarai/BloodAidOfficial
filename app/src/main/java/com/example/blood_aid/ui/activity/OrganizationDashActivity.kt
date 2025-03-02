package com.example.blood_aid.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blood_aid.R
import com.example.blood_aid.databinding.ActivityOrganizationDashBinding

class OrganizationDashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrganizationDashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityOrganizationDashBinding.inflate(layoutInflater)

        binding.bldRpo.setOnClickListener{
            startActivity(Intent(this,BloodRepositoryActivity::class.java))
        }
        binding.reqBtn.setOnClickListener{
            startActivity(Intent(this,RequestActivity::class.java))
        }
        setContentView(binding.root)
        binding.orgChg.setOnClickListener{
            startActivity(Intent(this,OrgProfileActivity::class.java))
        }
        binding.evntBtn.setOnClickListener{
            startActivity(Intent(this,EventActivity::class.java))
        }
        binding.bottomNotificationIcon.setOnClickListener{
            startActivity(Intent(this,NewsActivity::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}