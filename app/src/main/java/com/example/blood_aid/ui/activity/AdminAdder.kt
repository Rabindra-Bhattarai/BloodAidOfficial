package com.example.blood_aid.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.blood_aid.model.UserTypeModel
import com.example.blood_aid.repository.UserRepositoryImpl
import com.example.blood_aid.viewmodel.UserViewModel

class AdminAdder : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = UserViewModel(UserRepositoryImpl())

        val model: UserTypeModel = UserTypeModel("5uOd6LmOqwRypncjyZqFLXca1c62", "ADMN")
        userViewModel.addDataToDatabase("5uOd6LmOqwRypncjyZqFLXca1c62", model) { success,msg ->
            if (success) {
                Toast.makeText(this,"success",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
            }
        }
    }
}
