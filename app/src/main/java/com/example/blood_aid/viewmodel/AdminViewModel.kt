package com.example.blood_aid.viewmodel

import com.example.blood_aid.model.OrganizationModel
import com.example.blood_aid.repository.AdminRepository

class AdminViewModel(val repo:AdminRepository) {
    fun updateOrganization(userID:String,isEnabled:Boolean,callback:(Boolean,String)->Unit){
        repo.updateOrganization(userID,isEnabled,callback)
    }
    fun fetchOrganizations(callback: (List<OrganizationModel>, Boolean, String) -> Unit){
        repo.fetchOrganizations(callback)
    }
}