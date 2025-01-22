package com.example.blood_aid.viewmodel

import androidx.lifecycle.ViewModel
import com.example.blood_aid.model.UserTypeModel
import com.example.blood_aid.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(private val repo: UserRepository) : ViewModel() {

    // Login method
    fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        repo.login(email, password, callback)
    }

    // Signup method
    fun signup(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        repo.signup(email, password, callback)
    }

    // Forget password method
    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        repo.forgetPassword(email, callback)
    }

    // Add data to the database
    fun addDataToDatabase(
        userID: String,
        userModel: UserTypeModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.addDataToDatabase(userID, userModel, callback)
    }

    // Get the current logged-in user
    fun getCurrentUser(): FirebaseUser? {
        return repo.getCurrentUser()
    }

    // Fetch user type from the database
    fun getDataFromDB(userID: String, callback: (String) -> Unit) {
        repo.getDataFromDB(userID, callback)
    }

    // Logout method
    fun logout(callback: (Boolean, String) -> Unit) {
        repo.logout(callback)
    }
}
