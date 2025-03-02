package com.example.blood_aid.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.blood_aid.R
import com.example.blood_aid.databinding.ActivityEventBinding
import com.example.blood_aid.ui.fragments.CreateEventFragment
import com.example.blood_aid.ui.fragments.EndEventFragment
import com.example.blood_aid.ui.fragments.EventDetailsFragment

class EventActivity : AppCompatActivity() {
    private lateinit var binding:ActivityEventBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(EventDetailsFragment())

        binding.createEventButton.setOnClickListener{
            replaceFragment(CreateEventFragment())
        }
        binding.endEventButton.setOnClickListener{
            replaceFragment(EndEventFragment())
        }
        binding.eventDetailsButton.setOnClickListener{
            replaceFragment(EventDetailsFragment())
        }
        binding.backButton.setOnClickListener{
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}