package com.example.blood_aid.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blood_aid.R
import com.example.blood_aid.databinding.ActivityBloodDonationBinding
import com.example.blood_aid.model.BloodBankModel
import com.example.blood_aid.repository.BloodBankRepositoryImpl
import com.example.blood_aid.viewmodel.BloodRepoViewModel
import com.google.firebase.auth.FirebaseAuth

class BloodDonationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{
    private lateinit var binding:ActivityBloodDonationBinding
    private val bloods = arrayOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
    private lateinit var bloodGroup:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityBloodDonationBinding.inflate(layoutInflater)
        val spinner = binding.bloodTypeSpinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bloods)
        spinner.onItemSelectedListener = this
        spinner.adapter = adapter

        binding.cancelButton.setOnClickListener{
            finish()
        }

        binding.donateButton.setOnClickListener {
            val viewModel = BloodRepoViewModel(BloodBankRepositoryImpl())
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val bloodGroup = formatBloodGroup(bloodGroup)
            val amount = binding.givingInput.text.toString().toInt()

            if (amount <= 3) {
                if (userId != null) {
                    viewModel.getDataFromDB(userId) { bloodBank, b, s ->
                        val available = bloodBank?.getAvailableUnits(bloodGroup)?.toInt() ?: 0
                        if (available > 0 && available >= amount) {
                            val updatedValues = bloodBank?.let { updateBloodBank(it, bloodGroup, amount) }
                            // Do something with updatedValues, e.g., update it in the database
                            if (updatedValues != null) {
                                viewModel.editBloods(userId, updatedValues){
                                    success,message->
                                    if(success){
                                        Toast.makeText(this, "Successfully Donated Blood Reqirecting to Blood Repo", Toast.LENGTH_SHORT).show()
                                        finish()
                                    }
                                    else{
                                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                            Toast.makeText(this, "Blood donation successful", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Not Enough Blood to give", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Only 3 packets can be delivered to a person", Toast.LENGTH_SHORT).show()
            }
        }


        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        bloodGroup = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
    private fun formatBloodGroup(bldGrp: String): String {
        return when (bldGrp) {
            "A-" -> "a_NEGATIVE"
            "A+" -> "a_POSITIVE"
            "B-" -> "b_NEGATIVE"
            "B+" -> "b_POSITIVE"
            "O-" -> "o_NEGATIVE"
            "O+" -> "o_POSITIVE"
            "AB-" -> "ab_NEGATIVE"
            "AB+" -> "ab_POSITIVE"
            else -> "a_POSITIVE" // Default value
        }
    }
    fun updateBloodBank(bloodBank: BloodBankModel, bloodGroup: String, amount: Int): MutableMap<String, Any> {
        val updatedValues = mutableMapOf<String, Any>()

        when (bloodGroup) {
            "a_NEGATIVE" -> updatedValues["a_NEGATIVE"] = bloodBank.A_NEGATIVE - amount
            "a_POSITIVE" -> updatedValues["a_POSITIVE"] = bloodBank.A_POSITIVE - amount
            "b_NEGATIVE" -> updatedValues["b_NEGATIVE"] = bloodBank.B_NEGATIVE - amount
            "b_POSITIVE" -> updatedValues["b_POSITIVE"] = bloodBank.B_POSITIVE - amount
            "o_NEGATIVE" -> updatedValues["o_NEGATIVE"] = bloodBank.O_NEGATIVE - amount
            "o_POSITIVE" -> updatedValues["o_POSITIVE"] = bloodBank.O_POSITIVE - amount
            "ab_NEGATIVE" -> updatedValues["ab_NEGATIVE"] = bloodBank.AB_NEGATIVE - amount
            "ab_POSITIVE" -> updatedValues["ab_POSITIVE"] = bloodBank.AB_POSITIVE - amount
        }

        return updatedValues
    }



}