package com.example.blood_aid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blood_aid.R
import com.example.blood_aid.databinding.ActivityUserProfileBinding
import com.example.blood_aid.repository.IndividualRepositoryImpl
import com.example.blood_aid.repository.UserRepositoryImpl
import com.example.blood_aid.viewmodel.IndividualViewModel
import com.example.blood_aid.viewmodel.UserViewModel

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var individualViewModel: IndividualViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel = UserViewModel(UserRepositoryImpl())
        individualViewModel = IndividualViewModel(IndividualRepositoryImpl())

        val userId = userViewModel.getCurrentUser()?.uid.toString()
        displayData(userId)
        binding.backButton.setOnClickListener {
            finish()
            val intent = Intent(this@UserProfileActivity, UserDashActivity::class.java)
            startActivity(intent)
        }
        binding.changeDetailsButton.setOnClickListener {
            val intent = Intent(this@UserProfileActivity, ChangeDetailActivity::class.java)
            startActivity(intent)
        }

        binding.logoutButton.setOnClickListener {
            userViewModel.logout() { success, message ->
                if (success) {
                    Toast.makeText(this@UserProfileActivity, message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@UserProfileActivity, SplashActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun displayData(userID: String) {
        individualViewModel.getDataFromDB(userID)
        individualViewModel.userData.observe(this) { user ->
            user?.let {
                binding.userName.text = it.fullName
                binding.usrGender.text = it.gender
                binding.usrPhone.text = it.phoneNumber
                binding.citizenshipNum.text = it.citizenshipNumber
                binding.adressUsr.text = it.address
                binding.bldGrp.text = it.bloodGroup
                binding.email.text = it.email
            }
        }
    }
}