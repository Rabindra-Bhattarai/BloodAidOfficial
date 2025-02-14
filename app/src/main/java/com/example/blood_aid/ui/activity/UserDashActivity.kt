package com.example.blood_aid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blood_aid.R
import com.example.blood_aid.databinding.ActivityUserDashBinding

class UserDashActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityUserDashBinding
    private var bg= "A+";
    private val bloods = arrayOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserDashBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.usernameDash.text="Welcome to Blood Aid";
        val spinner = binding.bloodTypeSpinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bloods)
        spinner.onItemSelectedListener = this
        spinner.adapter = adapter

        binding.profileNav.setOnClickListener {
            val intent = Intent(this@UserDashActivity,UserProfileActivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        bg = p0?.getItemAtPosition(p2).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        bg = "A+"
    }
}