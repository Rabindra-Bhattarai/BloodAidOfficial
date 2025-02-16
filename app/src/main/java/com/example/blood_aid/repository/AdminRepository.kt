package com.example.blood_aid.repository

import com.example.blood_aid.model.OrganizationModel


interface AdminRepository {
    fun updateOrganization(userID:String,isEnabled:Boolean,callback:(Boolean,String)->Unit)
    fun fetchOrganizations(callback: (List<OrganizationModel>, Boolean, String) -> Unit)
}