package com.example.blood_aid.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.blood_aid.R
import com.example.blood_aid.databinding.FragmentEndEventBinding
import com.example.blood_aid.model.BloodBankModel
import com.example.blood_aid.viewmodel.EventsViewModel
import com.example.blood_aid.repository.BloodBankRepositoryImpl
import com.example.blood_aid.repository.EventRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EndEventFragment : Fragment() {

    private lateinit var binding: FragmentEndEventBinding
    private lateinit var viewModel: EventsViewModel
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEndEventBinding.inflate(inflater, container, false)
        viewModel = EventsViewModel(EventRepositoryImpl())
        database = FirebaseDatabase.getInstance().reference

        binding.continueButton.setOnClickListener {
            val userId = FirebaseAuth.getInstance().currentUser ?.uid ?: return@setOnClickListener

            // Check if the event exists for the OrgId
            checkEventExists(userId) { eventExists ->
                if (eventExists) {
                    // Proceed to end the event and update blood bank data
                    val bloodBankData = BloodBankModel(
                        OrgId = userId,
                        A_POSITIVE = binding.aPositiveInput.text.toString().toIntOrNull() ?: 0,
                        A_NEGATIVE = binding.aNegativeInput.text.toString().toIntOrNull() ?: 0,
                        B_POSITIVE = binding.bPositiveInput.text.toString().toIntOrNull() ?: 0,
                        B_NEGATIVE = binding.bNegativeInput.text.toString().toIntOrNull() ?: 0,
                        O_POSITIVE = binding.oPositiveInput.text.toString().toIntOrNull() ?: 0,
                        O_NEGATIVE = binding.oNegativeInput.text.toString().toIntOrNull() ?: 0,
                        AB_POSITIVE = binding.abPositiveInput.text.toString().toIntOrNull() ?: 0,
                        AB_NEGATIVE = binding.abNegativeInput.text.toString().toIntOrNull() ?: 0
                    )

                    viewModel.endEvent(userId, bloodBankData) { success, message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        if (success) {
                            Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "No event has been hosted for this OrgID.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    private fun checkEventExists(orgId: String, callback: (Boolean) -> Unit) {
        database.child("events").orderByChild("OrgId").equalTo(orgId).get()
            .addOnSuccessListener { snapshot ->
                callback(snapshot.exists())
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to check event status.", Toast.LENGTH_SHORT).show()
                callback(false)
            }
    }
}