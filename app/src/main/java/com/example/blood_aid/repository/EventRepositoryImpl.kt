package com.example.blood_aid.repository

import android.util.Log
import com.example.blood_aid.model.EventModel
import com.example.blood_aid.model.BloodBankModel
import com.example.blood_aid.viewmodel.UserViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EventRepositoryImpl : EventRepository {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val userViewModel: UserViewModel= UserViewModel(UserRepositoryImpl())

    override fun addEvent(event: EventModel, callback: (Boolean, String) -> Unit) {
        val eventId = userViewModel.getCurrentUser()?.uid.toString()
        database.child("events").child(eventId).setValue(event)
            .addOnSuccessListener {
                callback(true, "Event added with ID: $eventId")
            }
            .addOnFailureListener { e ->
                callback(false, "Error adding event: ${e.message}")
            }
    }

    override fun removeEvent(eventId: String, callback: (Boolean, String) -> Unit) {
        database.child("events").child(eventId).removeValue()
            .addOnSuccessListener {
                callback(true, "Event successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.e("RemoveEvent", "Error deleting event: ${e.message}")
                callback(false, "Error deleting event: ${e.message}")
            }
    }

    override fun endEvent(orgId: String, bloodBankData: BloodBankModel, callback: (Boolean, String) -> Unit) {
        database.child("BloodRepo").child(orgId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val existingData = snapshot.getValue(BloodBankModel::class.java) ?: BloodBankModel(orgId)

                // Update blood counts
                existingData.A_POSITIVE += bloodBankData.A_POSITIVE
                existingData.A_NEGATIVE += bloodBankData.A_NEGATIVE
                existingData.B_POSITIVE += bloodBankData.B_POSITIVE
                existingData.B_NEGATIVE += bloodBankData.B_NEGATIVE
                existingData.AB_POSITIVE += bloodBankData.AB_POSITIVE
                existingData.AB_NEGATIVE += bloodBankData.AB_NEGATIVE
                existingData.O_POSITIVE += bloodBankData.O_POSITIVE
                existingData.O_NEGATIVE += bloodBankData.O_NEGATIVE

                // Save updated data
                database.child("BloodRepo").child(orgId).setValue(existingData)
                    .addOnSuccessListener {
                        callback(true, "Blood bank updated successfully!")
                    }
                    .addOnFailureListener { e ->
                        callback(false, "Error updating blood bank: ${e.message}")
                    }
            } else {
                // If no existing data, create a new entry
                database.child("BloodRepo").child(orgId).setValue(bloodBankData)
                    .addOnSuccessListener {
                        callback(true, "Blood bank created successfully!")
                    }
                    .addOnFailureListener { e ->
                        callback(false, "Error creating blood bank: ${e.message}")
                    }
            }
        }.addOnFailureListener { e ->
            callback(false, "Error fetching blood bank data: ${e.message}")
        }
    }
    override fun getEventsByUserId(userId: String, callback: (List<EventModel>) -> Unit) {
        database.child("events").orderByChild("orgId").equalTo(userId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val events = mutableListOf<EventModel>()
                for (eventSnapshot in snapshot.children) {
                    val event = eventSnapshot.getValue(EventModel::class.java)
                    event?.let { events.add(it) }
                }
                callback(events)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList()) // Return empty list on failure
            }
        })
    }
    override fun checkEventExists(orgId: String, callback: (Boolean) -> Unit) {
        database.child("events").child(orgId).get()
            .addOnSuccessListener { snapshot ->
                callback(snapshot.exists())
            }
            .addOnFailureListener {
                callback(false) // Handle failure
            }
    }
    override fun getAllEvents(callback: (List<EventModel>) -> Unit) {
        database.child("events").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val events = mutableListOf<EventModel>()
                for (eventSnapshot in snapshot.children) {
                    val event = eventSnapshot.getValue(EventModel::class.java)
                    event?.let { events.add(it) }
                }
                callback(events)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList()) // Return empty list on failure
            }
        })
    }

}