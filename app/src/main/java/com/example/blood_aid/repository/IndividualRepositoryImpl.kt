package com.example.blood_aid.repository

import com.example.blood_aid.model.IndividualModel
import com.google.firebase.database.*

class IndividualRepositoryImpl : IndividualRepository {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference = database.reference.child("Individual")

    override fun addDataToDatabase(userID: String, userModel: IndividualModel?, callback: (Boolean, String) -> Unit) {
        if (userModel != null) {
            reference.child(userID).setValue(userModel).addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Registration Successful")
                } else {
                    callback(false, it.exception?.message.toString())
                }
            }
        } else {
            callback(false, "UserModel is null")
        }
    }

    override fun getDataFromDB(userID: String, callback: (IndividualModel?, Boolean, String) -> Unit) {
        reference.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val model = snapshot.getValue(IndividualModel::class.java)
                    callback(model, true, "Details fetched successfully")
                } else {
                    callback(null, false, "Data does not exist")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    override fun editProfile(userID: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit) {
        reference.child(userID).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Profile Edited Successfully")
            } else {
                callback(false, "Failed Editing Profile")
            }
        }
    }
}
