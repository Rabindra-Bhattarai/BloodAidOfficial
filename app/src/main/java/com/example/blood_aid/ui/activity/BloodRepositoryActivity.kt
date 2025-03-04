package com.example.blood_aid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blood_aid.R
import com.example.blood_aid.model.BloodBankModel
import com.example.blood_aid.repository.BloodBankRepositoryImpl
import com.example.blood_aid.databinding.ActivityBloodRepositoryBinding
import com.example.blood_aid.viewmodel.BloodRepoViewModel
import com.google.firebase.auth.FirebaseAuth

class BloodRepositoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBloodRepositoryBinding
    private val viewModel: BloodRepoViewModel = BloodRepoViewModel(BloodBankRepositoryImpl())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBloodRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.title.setOnClickListener {
            populate()
        }

        binding.donateButton.setOnClickListener {
            startActivity(Intent(this@BloodRepositoryActivity, BloodDonationActivity::class.java))
        }

        populate()
    }

    private fun populate() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            viewModel.getDataFromDB(userId) { bloodBank, success, message ->
                if (success) {
                    println(bloodBank)
                    updateUI(bloodBank)
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(bloodBank: BloodBankModel?) {
        binding.aPositiveValue.text = bloodBank?.A_POSITIVE.toString()
        binding.aNegativeValue.text = bloodBank?.A_NEGATIVE.toString()
        binding.bPositiveValue.text = bloodBank?.B_POSITIVE.toString()
        binding.bNegativeValue.text = bloodBank?.B_NEGATIVE.toString()
        binding.abPositiveValue.text = bloodBank?.AB_POSITIVE.toString()
        binding.abNegativeValue.text = bloodBank?.AB_NEGATIVE.toString()
        binding.oPositiveValue.text = bloodBank?.O_POSITIVE.toString()
        binding.oNegativeValue.text = bloodBank?.O_NEGATIVE.toString()
    }
}

