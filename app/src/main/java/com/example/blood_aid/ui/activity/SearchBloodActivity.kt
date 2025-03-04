package com.example.blood_aid.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blood_aid.R
import com.example.blood_aid.adapter.BloodBankAdapter
import com.example.blood_aid.adapter.OrgAdminAdapter
import com.example.blood_aid.databinding.ActivitySearchBloodBinding
import com.example.blood_aid.model.BloodBankModel
import com.example.blood_aid.model.OrganizationModel
import com.example.blood_aid.repository.AdminRepositoryImpl
import com.example.blood_aid.repository.BloodBankRepositoryImpl
import com.example.blood_aid.viewmodel.AdminViewModel
import com.example.blood_aid.viewmodel.BloodRepoViewModel

class SearchBloodActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBloodBinding
    private lateinit var adapter: BloodBankAdapter
    private val bloodRepoViewModel: BloodRepoViewModel = BloodRepoViewModel(BloodBankRepositoryImpl())
    private lateinit var blood:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBloodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonBack.setOnClickListener{
            finish()
        }
        val bldGrp = intent.getStringExtra("bloodGroup") ?: "A+"
        blood = formatBloodGroup(bldGrp)
        setupRecyclerView()
        observeViewModel()
        fetchOrganizations()

    }

    private fun formatBloodGroup(bldGrp: String): String {
        return when (bldGrp) {
            "A-" -> "a_NEGATIVE"
            "A+" -> "a_POSITIVE"
            "B-" -> "b_NEGATIVE"
            "B+" -> "b_POSITIVE"
            "O-" -> "o_NEGATIVE"
            "O+" -> "o_POSITIVE"
            "AB-" -> "ab_NEGATIVE"
            "AB+" -> "ab_POSITIVE"
            else -> "a_POSITIVE" // Default value
        }
    }


    private fun setupRecyclerView() {
         adapter = BloodBankAdapter(this, arrayListOf(),bloodRepoViewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        bloodRepoViewModel.bloodList.observe(this){
            data->
            adapter.updateData(data)
        }
    }

    private fun fetchOrganizations() {
        bloodRepoViewModel.searchBlood(blood)
    }

}