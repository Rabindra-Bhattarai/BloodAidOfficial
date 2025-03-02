package com.example.blood_aid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blood_aid.model.EventModel
import com.example.blood_aid.databinding.SingleEventItemBinding
import com.example.blood_aid.repository.OrganizationRepositoryImpl
import com.example.blood_aid.viewmodel.OrganizationViewModel
import androidx.lifecycle.Observer

class EventsAdapter(private var events: List<EventModel>) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {
    private var organizationViewModel: OrganizationViewModel = OrganizationViewModel(OrganizationRepositoryImpl())

    class EventViewHolder(private val binding: SingleEventItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventModel, organizationName: String) {
            binding.hostedBy.text = organizationName // Set the organization name
            binding.title.text = event.Title
            binding.eventDescription.text = event.Desc
            binding.eventDate.text = event.Date
            binding.eventTime.text = event.Time
            binding.eventVenue.text = event.Venue
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = SingleEventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]

        // Observe the organization data based on orgId
        organizationViewModel.getDataFromDB(event.orgId) // Fetch organization data
        organizationViewModel.userData.observeForever { organization ->
            if (organization != null) {
                holder.bind(event, organization.fullName) // Assuming fullName is the property for the organization's name
            } else {
                holder.bind(event, "Unknown Host") // Fallback if organization is not found
            }
        }
    }

    override fun getItemCount(): Int = events.size

    // Method to update the events list and notify the adapter
    fun update(newEvents: List<EventModel>) {
        events = newEvents
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }
}