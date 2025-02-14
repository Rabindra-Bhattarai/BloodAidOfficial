package com.example.blood_aid.repository

import com.example.blood_aid.model.BloodBankModel

interface BloodBankRepository {
    fun createBloodBank(userID:String, callback: (Boolean, String) -> Unit)
    fun getDataFromDB(userID: String,callback: (BloodBankModel?, Boolean, String) -> Unit)
    fun editBloods(userID: String,data:MutableMap<String,Any>,callback: (Boolean, String) -> Unit)  // <datatype,Any>
}