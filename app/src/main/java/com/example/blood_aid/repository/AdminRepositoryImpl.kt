package com.example.blood_aid.repository

import com.example.blood_aid.model.OrganizationModel
import com.google.firebase.database.*

class AdminRepositoryImpl : AdminRepository {
    private val database = FirebaseDatabase.getInstance().reference.child("Organization")

    override fun updateOrganization(
        userID: String, isEnabled: Boolean, callback: (Boolean, String) -> Unit
    ) {
        database.child(userID).child("enabled").setValue(isEnabled).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Organization updated successfully")
            } else {
                callback(false, it.exception?.message ?: "Error updating organization")
            }
        }
    }

    override fun fetchOrganizations(callback: (List<OrganizationModel>, Boolean, String) -> Unit) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            var organizations= mutableListOf<OrganizationModel>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for(eachProduct in snapshot.children){
                        var model = eachProduct.getValue(OrganizationModel::class.java)
                        if(model != null){
                            organizations.add(model)
                        }
                    }
                    callback(organizations, true, "Organizations fetched successfully")
                } else {
                    callback(emptyList(), false, "No organizations found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList(), false, error.message)
            }
        })
    }
}
