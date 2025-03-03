package com.example.blood_aid.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blood_aid.R
import com.example.blood_aid.adapter.BloodBankViewModel

class SearchBloodActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bloodBankAdapter: BloodBankAdapter
    private val bloodBankViewModel: BloodBankViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_blood)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        bloodBankAdapter = BloodBankAdapter()
        recyclerView.adapter = bloodBankAdapter

        val selectedBloodType = intent.getStringExtra("BLOOD_TYPE") ?: "A+"
        fetchBloodBanks(selectedBloodType)
    }

    private fun fetchBloodBanks(bloodType: String) {
        bloodBankViewModel.getBloodBanks(bloodType).observe(this) { bloodBanks ->
            bloodBankAdapter.submitList(bloodBanks)
        }
    }
}