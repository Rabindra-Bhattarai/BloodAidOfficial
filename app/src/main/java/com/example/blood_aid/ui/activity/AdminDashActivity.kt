package com.example.blood_aid.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.blood_aid.R
import com.example.blood_aid.databinding.ActivityAdminDashBinding
import com.example.blood_aid.repository.UserRepositoryImpl
import com.example.blood_aid.ui.fragments.OrganizationFragment
import com.example.blood_aid.viewmodel.UserViewModel
import com.example.firebaselearn.utils.LoadingUtils

class AdminDashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminDashBinding
    private lateinit var loadingUtils: LoadingUtils


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize View Binding
        binding = ActivityAdminDashBinding.inflate(layoutInflater)
        loadingUtils= LoadingUtils(this@AdminDashActivity)
        setContentView(binding.root)
        replaceFragment(OrganizationFragment())

        // Handle edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager : FragmentManager =
            supportFragmentManager

        val fragmentTransaction : FragmentTransaction =
            fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }

    private fun exit(){

        val userViewModel=UserViewModel(UserRepositoryImpl())
        userViewModel.logout(){
            success,message->
            if(success){
                val sharedPreferences=getSharedPreferences("loginInfo",Context.MODE_PRIVATE)
                sharedPreferences.edit().clear().apply()
                startActivity(Intent(this@AdminDashActivity,SplashActivity::class.java))
                finish()
            }else{
                Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
            }
        }
    }
    }

