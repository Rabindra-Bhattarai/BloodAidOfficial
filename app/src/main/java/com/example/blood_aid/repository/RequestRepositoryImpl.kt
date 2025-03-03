package com.example.blood_aid.repository

import com.example.blood_aid.model.RequestModel

class RequestRepositoryImpl : RequestRepository {

    private val requests = mutableListOf<RequestModel>()

    override fun addRequest(request: RequestModel) {
        requests.add(request)
    }

    override fun fetchAllRequests(): List<RequestModel> {
        return requests.filter { !isOldRequest(it) }
    }

    override fun deleteOldRequests() {
        requests.removeIf { isOldRequest(it) }
    }

    override fun isOldRequest(request: RequestModel): Boolean {
        val oneWeekInMillis = 7 * 24 * 60 * 60 * 1000 // 1 week in milliseconds
        return System.currentTimeMillis() - request.timestamp > oneWeekInMillis
    }
}