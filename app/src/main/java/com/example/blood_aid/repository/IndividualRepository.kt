package com.example.blood_aid.repository

import com.example.blood_aid.model.IndividualModel
import com.google.firebase.auth.FirebaseUser

interface IndividualRepository {
    fun addDataToDatabase(userID:String, userModel: IndividualModel?, callback: (Boolean, String) -> Unit)
    fun getDataFromDB(userID: String,callback: (IndividualModel?,Boolean, String) -> Unit)
    fun editProfile(userID: String,data:MutableMap<String,Any>,callback: (Boolean, String) -> Unit)  // <datatype,Any>
}