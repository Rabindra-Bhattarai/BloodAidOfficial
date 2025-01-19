package com.example.blood_aid.repository

import com.example.blood_aid.model.OrganizationModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrganizationRepositoryImpl : OrganizationRepository {
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference = database.reference.child("Organization")

    override fun addDataToDatabase(userID: String, userModel: OrganizationModel?, callback: (Boolean, String) -> Unit) {
        if (userModel != null) {
            reference.child(userModel.userId).setValue(userModel).addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Registration Successfully")
                } else {
                    callback(false, it.exception?.message.toString())
                }
            }
        } else {
            callback(false, "User model is null")
        }
    }

    override fun getDataFromDB(userID: String, callback: (OrganizationModel?, Boolean, String) -> Unit) {
        reference.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val model = snapshot.getValue(OrganizationModel::class.java)
                    callback(model, true, "Details fetched successfully")
                } else {
                    callback(null, false, "No data found for this userID")
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
                callback(false, "Failed Editing Profile: ${it.exception?.message}")
            }
        }
    }
}
