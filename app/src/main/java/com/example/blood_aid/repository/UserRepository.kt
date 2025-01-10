package com.example.blood_aid.repository

import com.example.blood_aid.model.UserTypeModel
import com.google.firebase.auth.FirebaseUser

interface UserRepository {
    fun getCurrentUser(): FirebaseUser?
    fun addDataToDatabase(userID:String, userModel: UserTypeModel?, callback: (Boolean, String) -> Unit)
    fun login(email:String,password:String,callback:(Boolean,String)->Unit)
    fun signup(email:String,password:String,callback: (Boolean, String, String) -> Unit)
    fun forgetPassword(email:String,callback: (Boolean, String) -> Unit)
    fun getDataFromDB(userID: String,callback: (UserTypeModel?,Boolean, String) -> Unit)
    fun logout(callback: (Boolean, String) -> Unit)
}
