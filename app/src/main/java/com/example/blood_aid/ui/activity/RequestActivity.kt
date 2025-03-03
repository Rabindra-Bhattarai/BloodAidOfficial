package com.example.blood_aid.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blood_aid.R
import com.example.blood_aid.ui.adapter.RequestAdapter
import com.example.blood_aid.viewmodel.RequestsViewModel
import com.example.blood_aid.repository.RequestRepositoryImpl // Use your repository implementation
import com.example.blood_aid.databinding.ActivityRequestBinding // Import the generated binding class

class RequestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequestBinding // Declare the binding variable
    private val requestViewModel: RequestsViewModel = RequestsViewModel(RequestRepositoryImpl())
    private lateinit var requestAdapter: RequestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView
        requestAdapter = RequestAdapter(mutableListOf())
        binding.requestsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.requestsRecyclerView.adapter = requestAdapter

        // Observe LiveData from ViewModel
        requestViewModel.getRequestsLiveData().observe(this) { requestList ->
            requestAdapter.updateRequests(requestList)
        }

        // Fetch all requests when the activity is created
        requestViewModel.fetchAllRequests()

        binding.backButton.setOnClickListener{
            finish()
        }
    }
}