package com.example.blood_aid.repository

import com.google.firebase.auth.FirebaseAuth

class AuthRepoImpl(var auth:FirebaseAuth) : AuthRepo {
    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Login Success")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun signup(
        email: String,
        password: String,
        callback: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Registration Success", auth.currentUser?.uid.toString())
            } else {
                callback(false, it.exception?.message.toString(), "")
            }
        }
    }

    override fun callback(b: Boolean, s: String, toString: String) {
        TODO("Not yet implemented")
    }
}