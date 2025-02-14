package com.example.blood_aid.repository

import com.example.blood_aid.model.BloodBankModel
import com.example.blood_aid.model.IndividualModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BloodBankRepositoryImpl:BloodBankRepository {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference = database.reference.child("BloodRepo")

    override fun createBloodBank(userID: String, callback: (Boolean, String) -> Unit) {
            val data=BloodBankModel(userID,0,0,0,0,0,0,0,0)
            reference.child(userID).setValue(data).addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Blood Repo Successfully made")
                } else {
                    callback(false, it.exception?.message.toString())
                }
            }
    }

    override fun getDataFromDB(userID: String, callback: (BloodBankModel?, Boolean, String) -> Unit) {
        reference.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val model = snapshot.getValue(BloodBankModel::class.java)
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

    override fun editBloods(userID: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit) {
        reference.child(userID).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Profile Edited Successfully")
            } else {
                callback(false, "Failed Editing Profile")
            }
        }
    }
}