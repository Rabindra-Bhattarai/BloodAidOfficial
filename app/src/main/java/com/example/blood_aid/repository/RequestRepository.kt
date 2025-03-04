package com.example.blood_aid.repository

import com.example.blood_aid.model.RequestModel

interface RequestRepository {
    suspend fun addRequest(request: RequestModel): String

   suspend fun fetchAllRequests(): List<RequestModel>

    suspend fun deleteOldRequests()

    fun isOldRequest(request: RequestModel): Boolean
}