package com.example.blood_aid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blood_aid.R
import com.example.blood_aid.databinding.ActivityChangePasswordBinding
import com.example.blood_aid.utils.LoadingUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingUtils= LoadingUtils(this)

        binding.buttonBack.setOnClickListener{
            finish()
        }

        binding.buttonContinue.setOnClickListener {
            chgPass()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun chgPass(){
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val newPassword= binding.editTextNewPassword.text.toString().trim()
        val confPassword=binding.editTextConfirmPassword.text.toString().trim()
        if (newPassword.isEmpty()) {
            binding.editTextNewPassword.error = "Password is required"
            binding.editTextNewPassword.requestFocus()
            return
        }
        if (newPassword!=confPassword) {
            binding.editTextConfirmPassword.error = "Passwords doesn't match"
            binding.editTextConfirmPassword.requestFocus()
            return
        }
        loadingUtils.show()
        user?.let {
            it.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        loadingUtils.dismiss()
                        Toast.makeText(this,"Password Changed Successfully \n Logging out in 5 seconds",Toast.LENGTH_LONG).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this,
                                UserLoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        },5000)
                    } else {
                        loadingUtils.dismiss()
                        Toast.makeText(this,task.exception.toString(),Toast.LENGTH_LONG).show()
                    }
                }
        } ?: run {
            loadingUtils.dismiss()
            Toast.makeText(this,"No user LoggedIn",Toast.LENGTH_LONG).show()
        }
    }
}
