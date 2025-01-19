package com.example.blood_aid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blood_aid.R
import com.example.blood_aid.databinding.ActivityConfirmPasswordBinding
import com.example.blood_aid.model.IndividualModel
import com.example.blood_aid.model.OrganizationModel
import com.example.blood_aid.model.UserTypeModel
import com.example.blood_aid.repository.IndividualRepositoryImpl
import com.example.blood_aid.repository.OrganizationRepositoryImpl
import com.example.blood_aid.repository.UserRepository
import com.example.blood_aid.repository.UserRepositoryImpl
import com.example.blood_aid.viewmodel.IndividualViewModel
import com.example.blood_aid.viewmodel.OrganizationViewModel
import com.example.blood_aid.viewmodel.UserViewModel

class ConfirmPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmPasswordBinding
    private lateinit var mainViewModel: UserViewModel
    private lateinit var individualViewModel: IndividualViewModel
    private lateinit var orgViewModel: OrganizationViewModel
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityConfirmPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ContinueButton.setOnClickListener { submit() }
        binding.BackButton.setOnClickListener { goBack() }

        type = intent.getStringExtra("Type").toString() ?: ""
        setupViewModels()
        setupUI()
    }

    private fun setupViewModels() {
        val userRepository = UserRepositoryImpl()
        mainViewModel = UserViewModel(userRepository)
        individualViewModel = IndividualViewModel(IndividualRepositoryImpl())
        orgViewModel = OrganizationViewModel(OrganizationRepositoryImpl())
    }

    private fun setupUI() {
        binding.BackButton.setOnClickListener { goBack() }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun containsSpecialCharacter(password: String): Boolean {
        val specialCharacters = "!@#\$%^&*()-_=+[]{}|;:'\",.<>?/`~\\"
        return password.any { it in specialCharacters }
    }

    private fun submit() {
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etCPassword.text.toString()

        if (password == confirmPassword) {
            if (password.length <= 8 || !containsSpecialCharacter(password)) {
                binding.etPassword.error = "Password must be at least 8 characters long and contain special characters."
            } else {
                handleSignUp()
            }
        } else {
            binding.etCPassword.error = "Passwords do not match."
        }
    }

    private fun handleSignUp() {
        val password = binding.etPassword.text.toString()
        if (type == "IND") {
            val userData = intent.getParcelableExtra<IndividualModel>("UserData")
            userData?.let { data ->
                mainViewModel.signup(data.email, password) { success, message, userId ->
                    if (success) {
                        data.userId = userId
                        individualViewModel.addDataToDatabase(
                            data.userId,
                            data
                        ) { success, message ->
                            showToast(message)
                            if (success) {
                                val typeV = UserTypeModel(data.userId, type)
                                val userViewModel = UserViewModel(UserRepositoryImpl())
                                userViewModel.addDataToDatabase(userId, typeV) { isdone, error ->
                                    if (isdone) {
                                        navigateTo(UserLoginActivity::class.java)
                                        showToast(message)
                                    }
                                }

                            }
                        }
                    } else {
                        showToast(message)
                    }
                }
            }
        } else if (type == "ORG") {
            val userData = intent.getParcelableExtra<OrganizationModel>("UserData")
            userData?.let { data ->
                mainViewModel.signup(data.email, password) { success, message, userId ->
                    if (success) {
                        val typeV = UserTypeModel(data.userId, type)
                        val userViewModel = UserViewModel(UserRepositoryImpl())
                        userViewModel.addDataToDatabase(userId, typeV) { isdone, error ->
                            if (isdone) {
                                navigateTo(UserLoginActivity::class.java)
                                showToast(message)
                            }
                        }

                    }

            else {
                showToast(message)
            }}}
        }


        }


        private fun goBack() {
            val intent = when (type) {
                "Org" -> Intent(this, OrgRegistrationActivity::class.java)
                "IND" -> Intent(this, NewRegistrationActivity::class.java)
                else -> Intent(this, StartActivity::class.java)
            }
            startActivity(intent)
        }

        private fun showToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        private fun <T> navigateTo(activity: Class<T>) {
            startActivity(Intent(this, activity))
        }
    }

