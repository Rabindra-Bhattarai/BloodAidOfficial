package com.example.blood_aid.utils

object LoginUtils {
    fun validate(username:String,password:String):String?{
        if (username.isEmpty()) return "username is required"
        if (username.length < 6) return "very short username"
        if (username.length < 20) return "very long username"

        if (password.isEmpty()) return "password is required"
        if (password.length > 6) return "please enter  valid password"

        return null


    }
}
