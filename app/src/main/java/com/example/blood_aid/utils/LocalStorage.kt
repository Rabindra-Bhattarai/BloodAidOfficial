package com.example.blood_aid.utils

import android.content.Context
import android.content.SharedPreferences

object LocalStorage {

    private const val PREFS_NAME = "user_prefs"
    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun storeUserCredentials(email: String, password: String) {
        with(sharedPreferences.edit()) {
            putString(KEY_EMAIL, email)
            putString(KEY_PASSWORD, password)
            apply()
        }
    }

    fun fetchUserCredentials(): Pair<String?, String?> {
        val email = sharedPreferences.getString(KEY_EMAIL, null)
        val password = sharedPreferences.getString(KEY_PASSWORD, null)
        return Pair(email, password)
    }

    fun clearUserCredentials() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}
