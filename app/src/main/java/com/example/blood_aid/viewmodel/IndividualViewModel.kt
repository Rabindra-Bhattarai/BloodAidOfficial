package com.example.blood_aid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.blood_aid.model.IndividualModel
import com.example.blood_aid.repository.IndividualRepository

class IndividualViewModel(val repo: IndividualRepository){
    fun addDataToDatabase(userID:String, userModel: IndividualModel, callback: (Boolean, String) -> Unit){
        repo.addDataToDatabase(userID,userModel,callback)
    }
    var _userData= MutableLiveData<IndividualModel?>()
    var userData= MutableLiveData<IndividualModel?>() //getter
        get() = _userData
    fun getDataFromDB(userID: String,callback: (IndividualModel?, Boolean, String) -> Unit){
        repo.getDataFromDB(userID){
                userModel,success,message->
            if(success){
                _userData.value = userModel
            }else{
                _userData.value = null
            }
        }
    }
    fun editProfile(userID: String,data:MutableMap<String,Any>,callback: (Boolean, String) -> Unit){
        repo.editProfile(userID, data, callback)
    }
}