package com.example.blood_aid.model

data class RequestModel(
    var id: Long = 0, // Add default values to fields
    var requestorName: String = "",
    var phoneNumber: String = "",
    var bloodGroup: String = "",
    var address: String = "",
    var timestamp: Long = 0
) {
    // No-argument constructor required for Firebase
    constructor() : this(0, "", "", "", "", 0)
}
