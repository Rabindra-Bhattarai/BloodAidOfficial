package com.example.blood_aid.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.blood_aid.R
import com.example.blood_aid.databinding.FragmentEventDetailsBinding
import com.example.blood_aid.model.EventModel
import com.example.blood_aid.viewmodel.EventsViewModel
import com.example.blood_aid.viewmodel.OrganizationViewModel
import com.example.blood_aid.repository.EventRepositoryImpl
import com.example.blood_aid.repository.OrganizationRepositoryImpl

class EventDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEventDetailsBinding
    private lateinit var eventsViewModel: EventsViewModel
    private lateinit var organizationViewModel: OrganizationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventDetailsBinding.inflate(inflater, container, false)

        // Initialize ViewModels
        eventsViewModel = EventsViewModel(EventRepositoryImpl())
        organizationViewModel = OrganizationViewModel(OrganizationRepositoryImpl())

        // Assuming you pass the OrgId as an argument to this fragment
        val orgId = arguments?.getString("OrgId") ?: return binding.root

        // Fetch organization details
        fetchOrganizationDetails(orgId)

        // Fetch event details
        fetchEventDetails(orgId)

        return binding.root
    }

    private fun fetchOrganizationDetails(orgId: String) {
        organizationViewModel.getDataFromDB(orgId) // Fetch organization data
        organizationViewModel.userData.observe(viewLifecycleOwner) { organization ->
            if (organization != null) {
                binding.eventHostText.text = organization.fullName // Update with the organization name
            } else {
                Toast.makeText(requireContext(), "Organization not found.", Toast.LENGTH_SHORT).show()
            }
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
                // No events found, show a message and navigate to CreateEventFragment
                Toast.makeText(requireContext(), "No events found for this organization", Toast.LENGTH_SHORT).show()
            }
        }
    }


}