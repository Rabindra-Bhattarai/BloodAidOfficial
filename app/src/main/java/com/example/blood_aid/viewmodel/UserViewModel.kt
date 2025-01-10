package com.example.blood_aid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.blood_aid.model.UserTypeModel
import com.example.blood_aid.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(val repo: UserRepository) {

    fun login(email:String,password:String,callback:(Boolean,String)->Unit){
        repo.login(email,password,callback)
    }
    fun signup(email:String,password:String,callback: (Boolean, String, String) -> Unit){
        repo.signup(email,password,callback)
    }
    fun forgetPassword(email:String,callback: (Boolean, String) -> Unit){
        repo.forgetPassword(email,callback)
    }
    fun addDataToDatabase(userID:String, userModel: UserTypeModel, callback: (Boolean, String) -> Unit){
        repo.addDataToDatabase(userID, userModel,callback)
    }
    fun getCurrentUser(): FirebaseUser?{
        return repo.getCurrentUser()
    }

    var _userData= MutableLiveData<UserTypeModel?>()
    var userData=MutableLiveData<UserTypeModel?>() //getter
        get() = _userData

    fun getDataFromDB(userID: String,callback: (UserTypeModel,Boolean, String) -> Unit){
        repo.getDataFromDB(userID){
            userModel,success,message->
            if(success){
                _userData.value = userModel
            }else{
                _userData.value = null
            }
        }
    }
    fun logout(callback: (Boolean, String) -> Unit){
        return repo.logout(callback)
    }

}