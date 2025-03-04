package com.example.blood_aid.repository

import android.util.Log
import com.example.blood_aid.model.OrganizationModel
import com.example.blood_aid.model.RequestModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

    override fun fetchRequests(callback: (List<RequestModel>, Boolean, String) -> Unit) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            var organizations = mutableListOf<RequestModel>()

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (eachProduct in snapshot.children) {
                        val model = eachProduct.getValue(RequestModel::class.java)
                        if (model != null) {
                            organizations.add(model)
                        }
                    }
                    Log.d("RequestRepository", "Fetched ${organizations.size} requests")
                    callback(organizations, true, "Requests fetched successfully")
                } else {
                    Log.d("RequestRepository", "No organizations found")
                    callback(emptyList(), false, "No organizations found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("RequestRepository", "Error fetching requests: ${error.message}")
                callback(emptyList(), false, error.message)
            }
        })
    }

    override suspend fun deleteOldRequests() {
            fetchRequests(){
                data,success,messege->
                data.forEach { request ->
                    if (isOldRequest(request)) {
                        database.child(request.id.toString()).removeValue() // Remove old requests
                    }
                }
            }

    }

    override fun isOldRequest(request: RequestModel): Boolean {
        val oneWeekInMillis = 7 * 24 * 60 * 60 * 1000 // 1 week in milliseconds
        return System.currentTimeMillis() - request.timestamp > oneWeekInMillis
    }
}
