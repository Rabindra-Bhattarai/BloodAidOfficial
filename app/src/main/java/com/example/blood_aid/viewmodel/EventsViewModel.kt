package com.example.blood_aid.viewmodel

import com.example.blood_aid.model.BloodBankModel
import com.example.blood_aid.model.EventModel
import com.example.blood_aid.repository.EventRepository

class EventsViewModel(private val repo: EventRepository) {
    fun addEvent(event: EventModel, callback: (Boolean, String) -> Unit) {
        repo.addEvent(event, callback)
    }

    fun removeEvent(eventId: String, callback: (Boolean, String) -> Unit) {
        repo.removeEvent(eventId, callback)
    }

    fun endEvent(orgId: String, bloodBankData: BloodBankModel, callback: (Boolean, String) -> Unit) {
        repo.endEvent(orgId, bloodBankData, callback)
    }
    fun getEventsByUserId(userId: String, callback: (List<EventModel>) -> Unit) {
        repo.getEventsByUserId(userId, callback)
    }
}