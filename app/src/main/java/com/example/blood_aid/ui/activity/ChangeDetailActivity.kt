package com.example.blood_aid.ui.activity

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.blood_aid.databinding.ActivityChangeDetailBinding
import com.example.blood_aid.model.IndividualModel
import com.example.blood_aid.repository.IndividualRepositoryImpl
import com.example.blood_aid.repository.UserRepositoryImpl
import com.example.blood_aid.viewmodel.IndividualViewModel
import com.example.blood_aid.viewmodel.UserViewModel

class ChangeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeDetailBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var individualViewModel: IndividualViewModel
    private lateinit var datas: IndividualModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChangeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel = UserViewModel(UserRepositoryImpl())
        individualViewModel = IndividualViewModel(IndividualRepositoryImpl())
        val userID= userViewModel.getCurrentUser()?.uid.toString()
        individualViewModel.getDataFromDB(userID)
        individualViewModel.userData.observe(this) { user ->
            user?.let {
                datas=it
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.saveButton.setOnClickListener {
            saveChanges()
        }
    }



    private fun saveChanges() {
        val fullName = binding.nameInput.text.toString()
        val email = datas.email
        val phone = binding.phoneInput.text.toString()
        val address = binding.addressInput.text.toString()
        val bloodGroup = datas.bloodGroup
        val gender = if (binding.maleRadioButton.isChecked) "Male" else "Female"

        var updatedMap= mutableMapOf<String,Any>()

        updatedMap["userId"]=datas.userId;
        updatedMap["address"]=datas.address;
        updatedMap["citizenshipNumber"]=datas.citizenshipNumber;
        updatedMap["email"]=datas.email;
        updatedMap["fullName"]=datas.fullName;
        updatedMap["bloodGroup"]=datas.bloodGroup;
        updatedMap["gender"]=datas.gender;

        if (validateInputs(fullName, email, phone, address, bloodGroup, gender)) {
            individualViewModel.editProfile(userViewModel.getCurrentUser()?.uid.toString(),updatedMap){
                success,message->
                if(success){
                    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validateInputs(
        fullName: String, email: String, phone: String, address: String,
        bloodGroup: String, gender: String
    ): Boolean {
        return when {
            fullName.isEmpty() -> {
                binding.nameInput.error = "Full Name cannot be empty"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.emailInput.error = "Invalid Email"
                false
            }
            phone.length !in 10..15 -> {
                binding.phoneInput.error = "Invalid Phone Number"
                false
            }
            address.isEmpty() -> {
                binding.addressInput.error = "Address cannot be empty"
                false
            }
            !isValidBloodGroup(bloodGroup) -> {
                binding.bloodGroupInput.error = "Invalid Blood Group"
                false
            }
            gender.isEmpty() -> {
                Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun isValidBloodGroup(bloodGroup: String): Boolean {
        val validBloodGroups = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
        return bloodGroup in validBloodGroups
    }
}
