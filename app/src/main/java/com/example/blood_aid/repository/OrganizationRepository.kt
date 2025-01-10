package com.example.blood_aid.repository


import com.example.blood_aid.model.OrganizationModel

interface OrganizationRepository {
    fun addDataToDatabase(userID:String, userModel: OrganizationModel?, callback: (Boolean, String) -> Unit)
    fun getDataFromDB(userID: String,callback: (OrganizationModel?, Boolean, String) -> Unit)
    fun editProfile(userID: String,data:MutableMap<String,Any>,callback: (Boolean, String) -> Unit)  // <datatype,Any>
}