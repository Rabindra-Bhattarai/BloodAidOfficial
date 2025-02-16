package com.example.blood_aid.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.blood_aid.databinding.FragmentConformBinding
import com.example.blood_aid.ui.activity.UserLoginActivity
import com.example.firebaselearn.utils.LoadingUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ConformFragment : Fragment() {
    private lateinit var binding: FragmentConformBinding
    private lateinit var loadingUtils: LoadingUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentConformBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ContinueButton.setOnClickListener{chgPass()}
    }
    private fun chgPass(){
        loadingUtils= LoadingUtils(requireActivity())

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val newPassword= binding.etPassword.text.toString().trim()
        val confPassword=binding.etCPassword.text.toString().trim()
        if (newPassword.isEmpty()) {
            binding.etPassword.error = "Password is required"
            binding.etPassword.requestFocus()
            return
        }
        if (newPassword!=confPassword) {
            binding.etCPassword.error = "Passwords doesn't match"
            binding.etCPassword.requestFocus()
            return
        }
        loadingUtils.show()
        user?.let {
            it.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        loadingUtils.dismiss()
                        Toast.makeText(requireContext(),"Password Changed Successfully \n Logging out in 5 seconds",Toast.LENGTH_LONG).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                                    val intent = Intent(requireContext(),
                                    UserLoginActivity::class.java)
                                startActivity(intent)
                                activity?.finish()
                          },5000)
                    } else {
                        loadingUtils.dismiss()
                        Toast.makeText(requireContext(),task.exception.toString(),Toast.LENGTH_LONG).show()
                    }
                }
        } ?: run {
            loadingUtils.dismiss()
            Toast.makeText(requireContext(),"No user LoggedIn",Toast.LENGTH_LONG).show()
        }
    }
    }