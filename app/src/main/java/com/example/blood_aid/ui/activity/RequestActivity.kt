package com.example.blood_aid.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blood_aid.adapter.RequestAdapter
import com.example.blood_aid.viewmodel.RequestsViewModel
import com.example.blood_aid.repository.RequestRepositoryImpl // Use your repository implementation
import com.example.blood_aid.databinding.ActivityRequestBinding // Import the generated binding class

class RequestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequestBinding // Declare the binding variable
    private val requestViewModel: RequestsViewModel=RequestsViewModel(RequestRepositoryImpl())
    private lateinit var requestAdapter: RequestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView with an empty list
        requestAdapter = RequestAdapter(this, arrayListOf(), requestViewModel)
        binding.requestsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.requestsRecyclerView.adapter = requestAdapter

        // Observe LiveData from ViewModel
        requestViewModel.getRequestsLiveData().observe(this) { requestList ->
            requestAdapter.updateData(requestList)
        }

        // Fetch all requests when the activity is created
        requestViewModel.fetchAllRequests()

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}