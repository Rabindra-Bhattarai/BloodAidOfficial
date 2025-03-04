package com.example.blood_aid.repository

import com.example.blood_aid.model.RequestModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await

class RequestRepositoryImpl : RequestRepository {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("requests")


    override suspend fun addRequest(request: RequestModel): String {
        return try {
            // Fetch all requests and check for existing request with the same phone number
            val snapshot = database.orderByChild("phoneNumber").equalTo(request.phoneNumber).get().await()
            val existingRequest = snapshot.children.mapNotNull { it.getValue<RequestModel>() }
                .find { it.phoneNumber == request.phoneNumber }

            if (existingRequest == null) {
                val requestId = database.push().key // Generate a unique ID for the request
                requestId?.let {
                    database.child(it).setValue(request)
                }
                "Request added successfully"
            } else {
                "Request already sent"
            }
        } catch (e: Exception) {
            "Failed to add request: ${e.message}"
        }
    }

    override suspend fun fetchAllRequests(): List<RequestModel> {
        return try {
            val snapshot = database.get().await() // Get all requests
            snapshot.children.mapNotNull { it.getValue<RequestModel>() }
        } catch (e: Exception) {
            emptyList() // Handle exceptions (e.g., network issues)
        }
        }
        override suspend fun deleteOldRequests() {
        val requests = fetchAllRequests()
        requests.forEach { request ->
            if (isOldRequest(request)) {
                database.child(request.id.toString()).removeValue() // Remove old requests
            }
        }
    }

    override fun isOldRequest(request: RequestModel): Boolean {
        val oneWeekInMillis = 7 * 24 * 60 * 60 * 1000 // 1 week in milliseconds
        return System.currentTimeMillis() - request.timestamp > oneWeekInMillis
    }
}
