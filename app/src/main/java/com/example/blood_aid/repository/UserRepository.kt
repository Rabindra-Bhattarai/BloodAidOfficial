package com.example.blood_aid.repository
//
//import com.example.firebaselearn.ui.UserModel
//import com.google.firebase.Firebase
//import com.google.firebase.auth.FirebaseUser
//
//interface UserRepository {
//    fun login(email:String,password:String,callback:(Boolean,String)->Unit)
//    fun signup(email:String,password:String,callback: (Boolean, String, String) -> Unit)
//    fun forgetPassword(email:String,callback: (Boolean, String) -> Unit)
//    fun addUserToDatabase(userID:String, userModel: UserModel, callback: (Boolean, String) -> Unit)
//    fun getCurrentUser():FirebaseUser?
//    fun getDataFromDB(userID: String,callback: (UserModel?,Boolean, String) -> Unit)
//    fun logout(callback: (Boolean, String) -> Unit)
//    fun editProfile(userID: String,data:MutableMap<String,Any>,callback: (Boolean, String) -> Unit)  // <datatype,Any>
//}
