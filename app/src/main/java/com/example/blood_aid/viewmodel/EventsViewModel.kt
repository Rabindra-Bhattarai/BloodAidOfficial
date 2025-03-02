package com.example.blood_aid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.blood_aid.model.BloodBankModel
import com.example.blood_aid.model.EventModel
import com.example.blood_aid.repository.EventRepository

class EventsViewModel(private val repo: EventRepository) {
    private val _events = MutableLiveData<List<EventModel>>()
    val events: LiveData<List<EventModel>> get() = _events
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
    fun checkEventExists(orgId: String, callback: (Boolean) -> Unit) {
        repo.checkEventExists(orgId, callback)
    }
    fun getAllEvents(callback: (List<EventModel>) -> Unit) {
        repo.getAllEvents(callback)
    }

    fun fetchAllEvents() {
        repo.getAllEvents { eventList ->
            _events.value = eventList
        }
    }
}