package com.example.blood_aid.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.blood_aid.R
import com.example.blood_aid.model.EventModel
import com.example.blood_aid.repository.EventRepositoryImpl
import com.example.blood_aid.viewmodel.EventsViewModel
import com.google.firebase.auth.FirebaseAuth

class CreateEventFragment : Fragment() {

    private lateinit var eventRepoViewModel: EventsViewModel
    private lateinit var eventTitleInput: EditText
    private lateinit var eventDateInput: EditText
    private lateinit var eventTimeInput: EditText
    private lateinit var eventVenueInput: EditText
    private lateinit var eventDescriptionInput: EditText
    private lateinit var createEventButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_event, container, false)

        // Initialize ViewModel
        eventRepoViewModel = EventsViewModel(EventRepositoryImpl())

        // Initialize UI elements
        eventTitleInput = view.findViewById(R.id.eventTitleInput)
        eventDateInput = view.findViewById(R.id.eventDateInput)
        eventTimeInput = view.findViewById(R.id.eventTimeInput)
        eventVenueInput = view.findViewById(R.id.eventVenueInput)
        eventDescriptionInput = view.findViewById(R.id.eventDescriptionInput)
        createEventButton = view.findViewById(R.id.createEventButton)

        // Set click listener for the create event button
        createEventButton.setOnClickListener {
            checkExistingEventAndCreate()
        }

        return view
    }

    private fun checkExistingEventAndCreate() {
        val userId = FirebaseAuth.getInstance().currentUser ?.uid ?: return

        eventRepoViewModel.getEventsByUserId(userId) { events ->
            if (events.isEmpty()) {
                if (validateInputs()) {
                    createEvent(userId)
                } else {
                    showAlert("Please fill in all fields.")
                }
            } else {
                showAlert("Only one event can be hosted at a time.")
            }
        }
    }

    private fun createEvent(userId: String) {
        val title = eventTitleInput.text.toString()
        val date = eventDateInput.text.toString()
        val time = eventTimeInput.text.toString()
        val venue = eventVenueInput.text.toString()
        val description = eventDescriptionInput.text.toString()

        val newEvent = EventModel(orgId = userId, Title = title, Date = date, Time = time, Venue = venue, Desc = description)

        eventRepoViewModel.addEvent(newEvent) { success, message ->
            if (success) {
                Toast.makeText(requireContext(), "Event created successfully!", Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                showAlert(message)
            }
        }
    }

    private fun clearFields() {
        eventTitleInput.text.clear()
        eventDateInput.text.clear()
        eventTimeInput.text.clear()
        eventVenueInput.text.clear()
        eventDescriptionInput.text.clear()
    }

    private fun showAlert(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun validateInputs(): Boolean {
        return eventTitleInput.text.isNotEmpty() &&
                eventDateInput.text.isNotEmpty() &&
                eventTimeInput.text.isNotEmpty() &&
                eventVenueInput.text.isNotEmpty() &&
                eventDescriptionInput.text.isNotEmpty()
    }
}