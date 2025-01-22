package com.example.blood_aid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blood_aid.model.OrganizationModel
import com.example.blood_aid.repository.AdminRepository

class AdminViewModel(private val repo: AdminRepository) : ViewModel() {
    private val _allOrg = MutableLiveData<List<OrganizationModel>?>()
    val allOrg: MutableLiveData<List<OrganizationModel>?> get() = _allOrg
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: MutableLiveData<Boolean> get() = _loadingState

    fun updateOrganization(userID: String, isEnabled: Boolean, callback: (Boolean, String) -> Unit) {
        repo.updateOrganization(userID, isEnabled, callback)
    }

    fun fetchOrganizations() {
        _loadingState.value = true
        repo.fetchOrganizations { products, success, _ ->
            if (success) {
                _allOrg.value = products
                _loadingState.value = false
            } else {
                _loadingState.value = false
            }
        }
    }
}
