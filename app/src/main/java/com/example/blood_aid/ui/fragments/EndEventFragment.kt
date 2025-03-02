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
import com.example.blood_aid.repository.EventRepositoryImpl
import com.example.blood_aid.repository.UserRepositoryImpl
import com.example.blood_aid.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class EndEventFragment : Fragment() {

    private lateinit var binding: FragmentEndEventBinding
    private lateinit var viewModel: EventsViewModel
    private lateinit var mainViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEndEventBinding.inflate(inflater, container, false)
        viewModel = EventsViewModel(EventRepositoryImpl())
        mainViewModel= UserViewModel(UserRepositoryImpl())
        binding.continueButton.setOnClickListener {
            val userId = mainViewModel.getCurrentUser()?.uid.toString()

            // Check if the event exists for the OrgId using the ViewModel
            viewModel.getEventsByUserId(userId) { snapshot ->
                if (!snapshot.isEmpty()) {
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

                    // Call the ViewModel to end the event
                    viewModel.endEvent(userId, bloodBankData) { success, message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        if (success) {
                            Toast.makeText(requireContext(), "Bloods updated successfully!", Toast.LENGTH_SHORT).show()
                            viewModel.removeEvent(userId){
                                success1,message1->
                                    if(success1){
                                        Toast.makeText(requireContext(), "Event Has Successfully ended", Toast.LENGTH_SHORT).show()
                                    }
                                else{
                                        Toast.makeText(requireContext(), message1, Toast.LENGTH_SHORT).show()
                                    }
                            }
                            clearFields() // Clear input fields after success
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "No event has been hosted for this OrgID.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    private fun clearFields() {
        binding.aPositiveInput.text.clear()
        binding.aNegativeInput.text.clear()
        binding.bPositiveInput.text.clear()
        binding.bNegativeInput.text.clear()
        binding.oPositiveInput.text.clear()
        binding.oNegativeInput.text.clear()
        binding.abPositiveInput.text.clear()
        binding.abNegativeInput.text.clear()
    }
}