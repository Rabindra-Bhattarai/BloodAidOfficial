package com.example.blood_aid.repository

import com.example.blood_aid.model.IndividualModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class IndividualRepositoryImpl: IndividualRepository {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference =database.reference.child("Individual")

    override fun addDataToDatabase(
        userID: String,
        userModel: IndividualModel,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(userModel.userId).setValue(userModel).addOnCompleteListener{
            if(it.isSuccessful){
                callback(true, "Registration Successfully")
            }
            else{
                callback(false,it.exception?.message.toString())
            }
        }
    }

    override fun getDataFromDB(userID: String, callback: (IndividualModel?, Boolean, String) -> Unit) {
        reference.child(userID).addValueEventListener( // continuoutsly refreshes
            object: ValueEventListener{  //hover garne and implement members
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val model= snapshot.getValue(IndividualModel::class.java)
                        callback(model,true,"Details fetched successfully")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null,false,error.message)
                }
            } //makes anonymus function
        )
    }

    override fun editProfile(
        userID: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(userID).updateChildren(data).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Profile Edited Successfully")
            }
            else{
                callback(false,"Failed Editing Profile")
            }
        }
    }
}