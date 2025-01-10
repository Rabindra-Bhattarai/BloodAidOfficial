package com.example.blood_aid.repository
//
//import android.view.Display.Mode
//import com.example.firebaselearn.ui.UserModel
//import com.example.firebaselearn.ui.fragment.ProfileFragment
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//
//class UserRepositoryImpl : UserRepository {
//    private var auth: FirebaseAuth =FirebaseAuth.getInstance()
//
//    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
//    private val reference: DatabaseReference =database.reference.child("users") //database.reference = root // child= new dir/table
//    override fun login(
//        email: String,
//        password: String,
//        callback: (Boolean, String) -> Unit
//    ) {
//        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{ //calling login func
//            if (it.isSuccessful){
//                callback(true,"Login Success")
//        }else{
//            callback(false,it.exception?.message.toString()) //passes error messege
//            }
//        }
//    }
//
//    override fun signup(
//        email: String,
//        password: String,
//        callback: (Boolean, String, String) -> Unit
//    ) {
//
//        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
//            if(it.isSuccessful) {
//                callback(true,"RegistrationSuccess",auth.currentUser?.uid.toString())
//            }
//            else{
//                callback(false,it.exception?.message.toString(),"")
//            }
//        }
//    }
//
//    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
//        auth.sendPasswordResetEmail(email).addOnCompleteListener {
//            if(it.isSuccessful){
//                callback(true,"passsword reset link is sent to $email")
//            }
//            else{
//                callback(false,it.exception?.message.toString())
//            }
//        }
//        }
//
//    override fun addUserToDatabase(
//        userID: String,
//        userModel: UserModel,
//        callback: (Boolean, String) -> Unit
//    ) {
//        reference.child(userModel.userId).setValue(userModel).addOnCompleteListener{
//            if(it.isSuccessful){
//                callback(true, "Registration Successfully")
//            }
//            else{
//                callback(false,it.exception?.message.toString())
//            }
//        }
//    }
//
//    override fun getCurrentUser(): FirebaseUser? {
//        return auth.currentUser
//    }
//
//    override fun getDataFromDB(userID: String, callback: (UserModel?, Boolean, String) -> Unit) {
//        reference.child(userID).addValueEventListener( // continuoutsly refreshes
//            object: ValueEventListener{  //hover garne and implement members
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()){
//                        val model= snapshot.getValue(UserModel::class.java)
//                        callback(model,true,"Details fetched successfully")
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    callback(null,false,error.message)
//                }
//            } //makes anonymus function
//        )
//    }
//
//    override fun logout(callback: (Boolean, String) -> Unit) {
//        try{
//            auth.signOut()
//            callback(true,"SignOut Successfully")
//            //shared preference.clear
//        }catch (e:Exception){
//            callback(false,"Error Signing out \nreason: $e")
//        }
//    }
//    override fun editProfile(
//        userID: String,
//        data: MutableMap<String, Any>,
//        callback: (Boolean, String) -> Unit
//    ) {
//            reference.child(userID).updateChildren(data).addOnCompleteListener {
//                if(it.isSuccessful){
//                    callback(true,"Profile Edited Successfully")
//                }
//                else{
//                    callback(false,"Failed Editing Profile")
//                }
//            }
//        }
//
//
//    private fun clearSharedPref(callback: (Boolean) -> Unit){
//        // TODO:  clears shared preferences
//    }
//
//
//}