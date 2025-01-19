package com.example.blood_aid.repository

import com.example.blood_aid.model.UserTypeModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserRepositoryImpl : UserRepository {
    private var auth: FirebaseAuth =FirebaseAuth.getInstance()

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference =database.reference.child("UserType") //database.reference = root // child= new dir/table
    override fun login(
        email: String,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{ //calling login func
            if (it.isSuccessful){
                callback(true,"Login Success")
        }else{
            callback(false,it.exception?.message.toString()) //passes error messege
            }
        }
    }

    override fun signup(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            if(it.isSuccessful) {
                callback(true,"Registration Success",auth.currentUser?.uid.toString())
            }
            else{
                callback(false,it.exception?.message.toString(),"")
            }
        }
    }

    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"passsword reset link is sent to $email")
            }
            else{
                callback(false,it.exception?.message.toString())
            }
        }
        }

    override fun addDataToDatabase(
        userID: String,
        userModel: UserTypeModel?,
        callback: (Boolean, String) -> Unit
    ) {
        if (userModel != null) {
            reference.child(userModel.userId).setValue(userModel).addOnCompleteListener{
                if(it.isSuccessful){
                    callback(true, "user Type Added")
                }
                else{
                    callback(false,it.exception?.message.toString())
                }
            }
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun getDataFromDB(userID: String, callback: (UserTypeModel?, Boolean, String) -> Unit) {
        reference.child(userID)
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var model = snapshot.getValue(UserTypeModel::class.java)
                        callback(model,true,"Data fetched")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null,false,error.message.toString())
                }

            })
    }

    override fun logout(callback: (Boolean, String) -> Unit) {
        try{
            auth.signOut()
            callback(true,"SignOut Successfully")
            //shared preference.clear
        }catch (e:Exception){
            callback(false,"Error Signing out \nreason: $e")
        }
    }




}