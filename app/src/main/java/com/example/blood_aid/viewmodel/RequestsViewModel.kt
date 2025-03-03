package com.example.blood_aid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blood_aid.model.RequestModel
import com.example.blood_aid.repository.RequestRepository

class RequestsViewModel(private val repository: RequestRepository){

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
        repository.addRequest(request)
        fetchAllRequests() // Refresh the list after adding a new request
    }

    fun fetchAllRequests() {
        repository.deleteOldRequests() // Remove old requests
        requestsLiveData.postValue(repository.fetchAllRequests().toMutableList())
    }

    fun getRequestsLiveData(): LiveData<MutableList<RequestModel>> {
        return requestsLiveData
    }
}