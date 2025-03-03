package com.example.blood_aid.repository

import com.example.blood_aid.model.RequestModel

interface RequestRepository {
    fun addRequest(request: RequestModel)

    fun fetchAllRequests(): List<RequestModel>

    fun deleteOldRequests()

    fun isOldRequest(request: RequestModel): Boolean
}