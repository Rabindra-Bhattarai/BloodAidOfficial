package com.example.blood_aid.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blood_aid.R
import com.example.blood_aid.databinding.ActivityOrgRegistrationBinding

class OrgRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrgRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize View Binding
        binding = ActivityOrgRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Handle Register button click
        binding.btnRegister.setOnClickListener {
            // Fetch input values using binding
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val phoneNumber = binding.etPhoneNumber.text.toString().trim()
            val address = binding.actAddress.text.toString().trim()
            val orgRegNumber = binding.etOrgRegNumber.text.toString().trim()
            val isAgreed = binding.cbAgree.isChecked

            // Validation
            if (name.isEmpty()) {
                showToast("Please enter the organization name.")
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                showToast("Please enter your email.")
                return@setOnClickListener
            }
            if (phoneNumber.isEmpty()) {
                showToast("Please enter your phone number.")
                return@setOnClickListener
            }
            if (address.isEmpty()) {
                showToast("Please enter your address.")
                return@setOnClickListener
            }
            if (orgRegNumber.isEmpty()) {
                showToast("Please enter your registration number.")
                return@setOnClickListener
            }
            if (!isAgreed) {
                showToast("You must agree to the terms and conditions.")
                return@setOnClickListener
            }

            // All fields are valid
            showToast("Registration successful!")
        }
    }

    // Helper function to show Toast messages
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
