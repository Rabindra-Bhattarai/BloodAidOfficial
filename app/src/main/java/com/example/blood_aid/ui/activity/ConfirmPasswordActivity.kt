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
import kotlin.math.E

class ConfirmPasswordActivity : AppCompatActivity() {
    private lateinit var binding:ActivityConfirmPasswordBinding
    private val type=intent.getStringExtra("Type").toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityConfirmPasswordBinding.inflate(layoutInflater)
        binding.BackButton.setOnClickListener{
            goBack()
        }
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
    private fun submit(){
        setContentView(binding.root)
//        val email=intent.getStringExtra("Email").toString()
        if((binding.etPassword.text.toString()) == binding.etCPassword.text.toString()){
            if(binding.etPassword.text.length<=8 && !containsSpecialCharacter(binding.etPassword.text.toString())){
                binding.etPassword.error="Password must be atleast 8 character Long and must contail special characters"
            }
        }else{
            binding.etCPassword.error="Passwords Does not Match"
        }


    }
    private fun goBack(){
        intent = when (type) {
            "Org" -> {
                Intent(this@ConfirmPasswordActivity,OrgRegistrationActivity::class.java)
            }
            "Ind" -> {
                Intent(this@ConfirmPasswordActivity,NewRegistrationActivity::class.java)
            }
            else -> {
                Intent(this@ConfirmPasswordActivity,StartActivity::class.java)
            }
        }
        startActivity(intent)
    }
}