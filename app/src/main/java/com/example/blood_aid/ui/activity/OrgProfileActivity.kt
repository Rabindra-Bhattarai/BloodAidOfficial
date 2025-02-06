package com.example.blood_aid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blood_aid.R
import com.example.blood_aid.databinding.ActivityOrgProfileBinding
import com.example.blood_aid.repository.OrganizationRepositoryImpl
import com.example.blood_aid.repository.UserRepositoryImpl
import com.example.blood_aid.viewmodel.OrganizationViewModel
import com.example.blood_aid.viewmodel.UserViewModel


class OrgProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrgProfileBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var organizationViewModel: OrganizationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding= ActivityOrgProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        userViewModel = UserViewModel(UserRepositoryImpl())
        organizationViewModel = OrganizationViewModel(OrganizationRepositoryImpl())

        var userId = userViewModel.getCurrentUser()?.uid.toString()
        binding.backButton.setOnClickListener {
            finish()
            val intent = Intent(this@OrgProfileActivity, OrganizationDashActivity::class.java)
            startActivity(intent)
        }

//        binding.changeDetailsButton.setOnClickListener {
//            // Intent to navigate to ChangeDetailActivity
//            val intent = Intent(this@OrgProfileActivity, ChangeDetailActivity::class.java)
//            startActivity(intent)
//        }


        binding.changeDetailsButton.setOnClickListener {
            Toast.makeText(this, "This feature is not implemented yet", Toast.LENGTH_SHORT).show()
        }

        binding.logoutButton.setOnClickListener {
            userViewModel.logout() { success, message ->
                if (success) {
                    Toast.makeText(this@OrgProfileActivity, message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@OrgProfileActivity, SplashActivity::class.java)
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
        organizationViewModel.getDataFromDB(userID)
        organizationViewModel.userData.observe(this) { user ->
            user?.let {
                binding.orgsName.text = it.fullName
                binding.orgsEmail.text = it.email
                binding.orgsNumber.text = it.phoneNumber
                binding.orgsAddress.text = it.address
                binding.orgsRegsNumber.text = it.registrationNumber
                binding.orgsNoh.text = it.enabled.toString()
                binding.orgsDoe.text =it.noOFTimesHosted.toInt().toString()
            }
        }
    }
}

