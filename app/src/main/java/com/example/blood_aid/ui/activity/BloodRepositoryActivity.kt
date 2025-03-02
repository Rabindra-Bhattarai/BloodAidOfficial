package com.example.blood_aid.ui.activity

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
    private val viewModel: BloodRepoViewModel= BloodRepoViewModel(BloodBankRepositoryImpl())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBloodRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        // Fetch blood bank data using the current user's ID
        val userId = FirebaseAuth.getInstance().currentUser ?.uid
        if (userId != null) {
            viewModel.getDataFromDB(userId){
                bloodBank, b, s ->
                binding.aPositiveValue.text = bloodBank?.A_POSITIVE.toString()
                binding.aNegativeValue.text = bloodBank?.A_NEGATIVE.toString()
                binding.bPositiveValue.text = bloodBank?.B_POSITIVE.toString()
                binding.bNegativeValue.text = bloodBank?.B_NEGATIVE.toString()
                binding.abPositiveValue.text = bloodBank?.AB_POSITIVE.toString()
                binding.abNegativeValue.text = bloodBank?.AB_NEGATIVE.toString()
                binding.oPositiveValue.text = bloodBank?.O_POSITIVE.toString()
                binding.oNegativeValue.text = bloodBank?.O_NEGATIVE.toString()
            }
        } else {
            Toast.makeText(this, "User  not logged in", Toast.LENGTH_SHORT).show()
        }
        }
    }
