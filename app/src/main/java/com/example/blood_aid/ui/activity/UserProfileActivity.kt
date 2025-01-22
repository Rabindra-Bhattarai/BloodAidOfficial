package com.example.blood_aid.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blood_aid.R
import com.example.blood_aid.databinding.ActivityUserProfileBinding
import com.example.blood_aid.viewmodel.IndividualViewModel
import com.example.blood_aid.viewmodel.UserViewModel

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var individualViewModel: IndividualViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}