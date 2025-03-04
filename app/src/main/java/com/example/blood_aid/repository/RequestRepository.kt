package com.example.blood_aid.repository

import com.example.blood_aid.model.RequestModel

interface RequestRepository {
    suspend fun addRequest(request: RequestModel): String

    fun fetchRequests(callback: (List<RequestModel>, Boolean, String) -> Unit)

    suspend fun deleteOldRequests()

    fun isOldRequest(request: RequestModel): Boolean
}