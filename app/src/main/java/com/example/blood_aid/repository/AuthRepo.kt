package com.example.blood_aid.repository

interface AuthRepo {
    fun login(email:String,password:String,callback:(Boolean,String)->Unit)
    fun signup(email:String, password:String, callback: (Boolean, String?) -> Unit)
    abstract fun callback(b: Boolean, s: String, toString: String)
}