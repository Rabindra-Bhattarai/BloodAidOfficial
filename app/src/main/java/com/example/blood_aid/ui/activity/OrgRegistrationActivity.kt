package com.example.blood_aid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blood_aid.databinding.ActivityOrgRegistrationBinding
import com.example.blood_aid.model.OrganizationModel
import com.example.blood_aid.utils.LoadingUtils

class OrgRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrgRegistrationBinding

    private lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize View Binding
        binding = ActivityOrgRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingUtils= LoadingUtils(this@OrgRegistrationActivity)

        // Handle edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.tvSubtitle.setOnClickListener{
        startActivity(Intent(this,UserLoginActivity::class.java))
        finish()
        }
        binding.btnRightSide.setOnClickListener{
            finish()
        }

        // Handle Register button click
        binding.orgBtnRegister.setOnClickListener {
            // Fetch input values using binding
            val name = binding.orgName.text.toString().trim()
            val email = binding.orgtEmail.text.toString().trim()
            val phoneNumber = binding.orgPhoneNumber.text.toString().trim()
            val address = binding.orgAddress.text.toString().trim()
            val orgRegNumber = binding.orgRegNumber.text.toString().trim()
            val isAgreed = binding.orgAgree.isChecked

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
            loadingUtils.show()
            val model= OrganizationModel(
                "",
                name,
                email,
                phoneNumber,
                address,
                orgRegNumber,
                false,
                0
            )
                val intent= Intent(this@OrgRegistrationActivity,ConfirmPasswordActivity::class.java)
                intent.putExtra("UserData",model)
                intent.putExtra("Type","ORG")
            loadingUtils.dismiss()
                startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        loadingUtils.dismiss()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
