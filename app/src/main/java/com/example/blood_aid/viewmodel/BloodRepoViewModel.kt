package com.example.blood_aid.viewmodel

import com.example.blood_aid.model.BloodBankModel
import com.example.blood_aid.repository.BloodBankRepository

class BloodRepoViewModel(val repo:BloodBankRepository) {
    fun createBloodBank(userID:String, callback: (Boolean, String) -> Unit){
        repo.createBloodBank(userID,callback)
    }
    fun getDataFromDB(userID: String,callback: (BloodBankModel?, Boolean, String) -> Unit){
        repo.getDataFromDB(userID,callback)
    }
    fun editBloods(userID: String,data:MutableMap<String,Any>,callback: (Boolean, String) -> Unit){
        repo.editBloods(userID,data,callback)
    }
}