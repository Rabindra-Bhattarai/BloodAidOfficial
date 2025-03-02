package com.example.blood_aid.repository

import com.example.blood_aid.model.EventModel
import com.example.blood_aid.model.BloodBankModel

interface EventRepository {
    fun addEvent(event: EventModel, callback: (Boolean, String) -> Unit)
    fun removeEvent(eventId: String, callback: (Boolean, String) -> Unit)
    fun endEvent(orgId: String, bloodBankData: BloodBankModel, callback: (Boolean, String) -> Unit)
    fun getEventsByUserId(userId: String, callback: (List<EventModel>) -> Unit)
    fun checkEventExists(orgId: String, callback: (Boolean) -> Unit)
    fun getAllEvents(callback: (List<EventModel>) -> Unit)
}