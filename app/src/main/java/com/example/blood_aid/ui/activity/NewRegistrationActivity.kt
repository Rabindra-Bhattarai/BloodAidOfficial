package com.example.blood_aid.ui.activity

import android.content.Intent
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
import com.example.blood_aid.databinding.ActivityNewRegistrationBinding
import com.example.blood_aid.model.IndividualModel
import com.example.firebaselearn.utils.LoadingUtils

class NewRegistrationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityNewRegistrationBinding
    private lateinit var bloodGroup: String
    private var gender: String = "Male"
    private lateinit var loadingUtils: LoadingUtils
    private val bloods = arrayOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNewRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner = binding.spinnerBloodGroup
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bloods)
        spinner.onItemSelectedListener = this
        spinner.adapter = adapter

        loadingUtils = LoadingUtils(this@NewRegistrationActivity)

        binding.rbMale.setOnClickListener {
            gender = "MALE"
        }
        binding.rbFemale.setOnClickListener {
            gender = "FEMALE"
        }

        binding.etBtnRegister.setOnClickListener {
            loadingUtils.show()
            val email = binding.etEmail.text.toString()
            val fullname = binding.etFullName.text.toString()
            val number = binding.etMobileNumber.text.toString()
            val citizenship = binding.etCitizenship.text.toString()
            val address = binding.actAddress.text.toString()
            val model = IndividualModel(
                "",
                fullname,
                email,
                number,
                address,
                citizenship,
                bloodGroup,
                gender,
                0
            )
            if (binding.cbTerms.isChecked) {
                val intent = Intent(this@NewRegistrationActivity, ConfirmPasswordActivity::class.java)
                intent.putExtra("UserData", model)
                intent.putExtra("Type", "IND")
                startActivity(intent)
            } else {
                loadingUtils.dismiss()
                Toast.makeText(this@NewRegistrationActivity, "Please agree to Terms and Agreement", Toast.LENGTH_SHORT).show()
            }
        }

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
        // Handle no selection case if needed
    }
}
