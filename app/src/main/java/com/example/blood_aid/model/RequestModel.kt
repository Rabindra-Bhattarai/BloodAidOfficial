package com.example.blood_aid.model

data class RequestModel(
    val id: Long,
    val requestorName: String,
    val phoneNumber: String,
    val bloodGroup: String,
    val address: String,
    val timestamp: Long // To track when the request was created
)