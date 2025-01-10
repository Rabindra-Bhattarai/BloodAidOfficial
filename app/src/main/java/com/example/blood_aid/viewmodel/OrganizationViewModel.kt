package com.example.blood_aid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.blood_aid.model.OrganizationModel
import com.example.blood_aid.repository.OrganizationRepository

class OrganizationViewModel (val repo: OrganizationRepository){
    fun addDataToDatabase(userID:String, userModel: OrganizationModel, callback: (Boolean, String) -> Unit){
        repo.addDataToDatabase(userID,userModel,callback)
    }
    var _userData= MutableLiveData<OrganizationModel?>()
    var userData= MutableLiveData<OrganizationModel?>() //getter
        get() = _userData
    fun getDataFromDB(userID: String,callback: (OrganizationModel?, Boolean, String) -> Unit){
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