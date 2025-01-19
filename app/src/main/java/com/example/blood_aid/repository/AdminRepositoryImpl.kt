package com.example.blood_aid.repository

import android.util.Log
import com.example.blood_aid.model.OrganizationModel
import com.google.firebase.database.*

class AdminRepositoryImpl : AdminRepository {
    private val reference: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("Organization")

    override fun updateOrganization(
        userID: String,
        isEnabled: Boolean,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(userID).child("enabled").setValue(isEnabled)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "Organization updated successfully")
                } else {
                    callback(false, "Update failed: ${task.exception?.message}")
                }
            }
    }

    override fun fetchOrganizations(callback: (List<OrganizationModel>, Boolean, String) -> Unit) {
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val organizations = snapshot.children.mapNotNull {
                        it.getValue(OrganizationModel::class.java)
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
