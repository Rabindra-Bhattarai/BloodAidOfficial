package com.example.blood_aid.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blood_aid.R
import com.example.blood_aid.databinding.ActivityBloodDonationBinding

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
}