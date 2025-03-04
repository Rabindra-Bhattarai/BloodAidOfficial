package com.example.blood_aid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blood_aid.model.RequestModel
import com.example.blood_aid.repository.RequestRepository
import kotlinx.coroutines.launch

class RequestsViewModel(private val repository: RequestRepository) : ViewModel() {

    private val requestsLiveData = MutableLiveData<MutableList<RequestModel>>()

    fun addRequest(requestorName: String, phoneNumber: String, bloodGroup: String, address: String) {
        val request = RequestModel(
            id = System.currentTimeMillis(), // Using current time as ID for simplicity
            requestorName = requestorName,
            phoneNumber = phoneNumber,
            bloodGroup = bloodGroup,
            address = address,
            timestamp = System.currentTimeMillis()
        )

        // Launch a coroutine to add the request and then fetch all requests
        viewModelScope.launch {
            repository.addRequest(request) // Add the request to the repository
            fetchAllRequests() // Refresh the list after adding a new request
        }
    }

    fun fetchAllRequests() {
        viewModelScope.launch {
            try {
                repository.deleteOldRequests() // Remove old requests
                val requests = repository.fetchAllRequests() // Fetch all requests from the repository
                Log.d("RequestsViewModel", "Fetched requests: ${requests.size}")
                requestsLiveData.postValue(requests.toMutableList()) // Update LiveData
            } catch (e: Exception) {
                Log.e("RequestsViewModel", "Error fetching requests", e)
            }
        }
    }

    fun getRequestsLiveData(): LiveData<MutableList<RequestModel>> {
        return requestsLiveData
    }
}
