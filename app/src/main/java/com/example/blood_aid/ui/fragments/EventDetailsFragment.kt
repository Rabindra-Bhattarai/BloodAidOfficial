package com.example.blood_aid.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.blood_aid.R
import com.example.blood_aid.databinding.FragmentEventDetailsBinding
import com.example.blood_aid.viewmodel.EventsViewModel
import com.example.blood_aid.viewmodel.OrganizationViewModel
import com.example.blood_aid.repository.EventRepositoryImpl
import com.example.blood_aid.repository.OrganizationRepositoryImpl
import com.example.blood_aid.repository.UserRepositoryImpl
import com.example.blood_aid.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class EventDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEventDetailsBinding
    private lateinit var eventsViewModel: EventsViewModel
    private lateinit var organizationViewModel: OrganizationViewModel
    private lateinit var userViewModel: UserViewModel
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var fetchRunnable: Runnable
    private lateinit var orgId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventDetailsBinding.inflate(inflater, container, false)

        // Initialize ViewModels
        eventsViewModel =EventsViewModel(EventRepositoryImpl())
        organizationViewModel = OrganizationViewModel(OrganizationRepositoryImpl())
        userViewModel= UserViewModel(UserRepositoryImpl())
        // Fetch organization details and events
        fetchOrganizationAndEventDetails()

        // Set up the runnable to fetch event details every 5 seconds
        fetchRunnable = Runnable {
            val orgId = userViewModel.getCurrentUser()?.uid
            if (orgId != null) {
                fetchEventDetails(orgId) // Fetch events using the orgId
            }
            handler.postDelayed(fetchRunnable, 5000) // Repeat every 5 seconds
        }
        handler.post(fetchRunnable) // Start the fetching process

        return binding.root
    }

    private fun fetchOrganizationAndEventDetails() {
        val userId = userViewModel.getCurrentUser()?.uid
        if (userId != null) {
            organizationViewModel.getDataFromDB(userId) // Fetch organization data
            organizationViewModel.userData.observe(viewLifecycleOwner) { organization ->
                if (organization != null) {
                    binding.eventHostText.text = organization.fullName // Update with the organization name
                    fetchEventDetails(organization.userId) // Fetch events using the organization ID
                } else {
                    Toast.makeText(requireContext(), "Organization not found.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "User  not logged in.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchEventDetails(orgId: String) {
        eventsViewModel.getEventsByUserId(orgId) { events ->
            if (events.isNotEmpty()) {
                val event = events[0] // Assuming you want the first event
                binding.eventTitleText.text = event.Title
                binding.eventDateText.text = event.Date
                binding.eventTimeText.text = event.Time
                binding.eventVenueText.text = event.Venue
                binding.eventDescriptionText.text = event.Desc
            } else {
                // No events found, you can choose to do nothing or log it
                Toast.makeText(requireContext(), "No events found for this organization.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(fetchRunnable) // Stop the handler when the view is destroyed
    }
}