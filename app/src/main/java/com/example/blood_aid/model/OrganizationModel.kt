package com.example.blood_aid.model

data class OrganizationModel(
    var userId:String= "",
    var fullName:String="",
    var email:String="",
    var phoneNumber:String="",
    var address:String="",
    var registrationNumber:String="",
    var enabled:Boolean=false
) {
}