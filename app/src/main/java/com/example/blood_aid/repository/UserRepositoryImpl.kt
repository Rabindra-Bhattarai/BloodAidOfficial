package com.example.blood_aid.repository

import android.content.Context
import com.example.blood_aid.model.UserTypeModel
import com.example.blood_aid.utils.LocalStorage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class UserRepositoryImpl : UserRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference = database.reference.child("UserType")

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Login Success")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun signup(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Registration Success", auth.currentUser?.uid.toString())
            } else {
                callback(false, it.exception?.message.toString(), "")
            }
        }
    }

    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Password reset link is sent to $email")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    override fun addDataToDatabase(userID: String, userModel: UserTypeModel?, callback: (Boolean, String) -> Unit) {
        if (userModel != null) {
            reference.child(userID).setValue(userModel).addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "User type added successfully")
                } else {
                    callback(false, it.exception?.message.toString())
                }
            }
        } else {
            callback(false, "UserTypeModel is null")
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun getDataFromDB(userID: String, callback: (String) -> Unit) {
        reference.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userType = snapshot.child("userType").getValue(String::class.java) ?: "ERROR"
                    callback(userType)
                } else {
                    callback("ERROR")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback("ERROR")
            }
        })
    }

    override fun logout(callback: (Boolean, String) -> Unit) {
        try {
            auth.signOut()
            LocalStorage.clearUserCredentials()
            callback(true, "Sign out successfully")
        } catch (e: Exception) {
            callback(false, "Error signing out: ${e.message}")
        }
    }
}
