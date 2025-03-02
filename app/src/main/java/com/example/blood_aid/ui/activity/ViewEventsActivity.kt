package com.example.blood_aid.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blood_aid.R
import com.example.blood_aid.model.EventModel
import com.example.blood_aid.adapter.EventsAdapter
import com.example.blood_aid.viewmodel.EventsViewModel
import com.example.blood_aid.repository.EventRepositoryImpl
import com.example.blood_aid.databinding.FragmentEndEventBinding
import com.example.blood_aid.databinding.ActivityViewEventsBinding

class ViewEventsActivity : AppCompatActivity() {

    private lateinit var viewModel: EventsViewModel
    private lateinit var binding: ActivityViewEventsBinding // Assuming you are using View Binding
    private lateinit var adapter: EventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setOnClickListener{
            finish()
        }
        viewModel = EventsViewModel(EventRepositoryImpl())

        setupRecyclerView()
        fetchAllEvents()
    }

    private fun setupRecyclerView() {
        adapter = EventsAdapter(emptyList()) // Initialize with an empty list
        binding.eventsRecyclerView.adapter = adapter
        binding.eventsRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun fetchAllEvents() {
        viewModel.fetchAllEvents() // Fetch all events from the ViewModel
        viewModel.events.observe(this) { events ->
            adapter.update (events) // Update the adapter with the fetched events
        }
    }
}