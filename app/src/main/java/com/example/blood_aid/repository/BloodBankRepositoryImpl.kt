package com.example.blood_aid.repository

import com.example.blood_aid.model.BloodBankModel
import com.example.blood_aid.model.IndividualModel
import com.example.blood_aid.model.OrganizationModel
import com.example.blood_aid.viewmodel.OrganizationViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BloodBankRepositoryImpl : BloodBankRepository {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference = database.reference.child("BloodRepo")

    override fun createBloodBank(userID: String, callback: (Boolean, String) -> Unit) {
        val data = BloodBankModel(userID, 0, 0, 0, 0, 0, 0, 0, 0)
        reference.child(userID).setValue(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Blood Repo Successfully made")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun getDataFromDB(
        userID: String,
        callback: (BloodBankModel?, Boolean, String) -> Unit
    ) {
        // Reference to the specific userID in the database
        val userReference = reference.child(userID)

        // Add a listener to fetch data
        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Attempt to convert the snapshot to BloodBankModel
                    val model = snapshot.getValue(BloodBankModel::class.java)
                    if (model != null) {
                        callback(model, true, "Details fetched successfully")
                        println("Data fetched for userID: $userID - $model") // Log the fetched data
                    } else {
                        callback(null, false, "Data could not be parsed")
                        println("Data could not be parsed for userID: $userID") // Log if parsing fails
                    }
                } else {
                    callback(null, false, "Data does not exist")
                    println("No data found for userID: $userID") // Log if no data found
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
                println("Error fetching data for userID: $userID - ${error.message}") // Log the error
            }
        })
    }

    override fun editBloods(
        userID: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(userID).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Profile Edited Successfully")
            } else {
                callback(false, "Failed Editing Profile")
            }
        }
    }

    override fun searchBlood(
        bloodGroup: String,
        callback: (List<OrganizationModel>, Boolean, String) -> Unit
    ) {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val organizations = mutableListOf<OrganizationModel>()
                    var pendingCount = snapshot.childrenCount

                    for (orgSnapshot in snapshot.children) {
                        val bloodBank = orgSnapshot.getValue(BloodBankModel::class.java)
                        println("Blood Bank: $bloodBank")

                        bloodBank?.let {
                            if (it.getAvailableUnits(bloodGroup) > 0) {
                                fetchOrganizationData(it.OrgId, object : DataFetchListener {
                                    override fun onDataFetched(organization: OrganizationModel?) {
                                        organization?.let { org ->
                                            organizations.add(org)
                                            println("Organization added: ${org.fullName}")
                                        } ?: println("No organization data found for OrgId: ${it.OrgId}")

                                        pendingCount--
                                        if (pendingCount.toInt() == 0) {
                                            callback(organizations, true, "Organizations fetched successfully")
                                            println("Organizations: $organizations")
                                        }
                                    }
                                })
                            } else {
                                pendingCount--
                                if (pendingCount.toInt() == 0) {
                                    callback(organizations, true, "Organizations fetched successfully")
                                    println("Organizations: $organizations")
                                }
                            }
                        }
                    }
                } else {
                    callback(emptyList(), false, "No Bloods Available")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList(), false, error.message)
            }
        })
    }

    private fun fetchOrganizationData(orgId: String, listener: DataFetchListener) {
        val orgViewModel = OrganizationViewModel(OrganizationRepositoryImpl())
        orgViewModel.getDataFromDB(orgId)
        orgViewModel.userData.observeForever { organization ->
            listener.onDataFetched(organization)
        }
    }
}
interface DataFetchListener {
    fun onDataFetched(organization: OrganizationModel?)
}



