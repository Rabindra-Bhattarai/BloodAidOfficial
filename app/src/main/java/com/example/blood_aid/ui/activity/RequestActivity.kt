package com.example.blood_aid.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blood_aid.adapter.RequestAdapter
import com.example.blood_aid.viewmodel.RequestsViewModel
import com.example.blood_aid.repository.RequestRepositoryImpl
import com.example.blood_aid.databinding.ActivityRequestBinding
import com.example.blood_aid.repository.RequestRepository

class RequestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequestBinding
    private val requestViewModel: RequestsViewModel by viewModels {
        RequestsViewModelFactory(RequestRepositoryImpl())
    }
    private lateinit var requestAdapter: RequestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView with an empty list
        requestAdapter = RequestAdapter(this, arrayListOf())
        binding.requestsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.requestsRecyclerView.adapter = requestAdapter

        // Observe LiveData from ViewModel
        requestViewModel.allOrg.observe(this, Observer { requestList ->
            Log.d("RequestActivity", "Observed request list with ${requestList?.size} items")
            requestAdapter.updateData(requestList)
        })

        // Fetch all requests when the activity is created
        requestViewModel.fetchAllRequests()

        // Handle the back button click
        binding.backButton.setOnClickListener {
            finish()
        }
    }

}
class RequestsViewModelFactory(
    private val repository: RequestRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RequestsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RequestsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}