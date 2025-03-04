package com.example.blood_aid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blood_aid.model.BloodBankModel
import com.example.blood_aid.model.OrganizationModel
import com.example.blood_aid.repository.BloodBankRepository
import kotlinx.coroutines.launch

class BloodRepoViewModel(private val repo: BloodBankRepository)  {

    private val _bloodList = MutableLiveData<List<OrganizationModel>>()
    val bloodList: LiveData<List<OrganizationModel>> = _bloodList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun createBloodBank(userID: String, callback: (Boolean, String) -> Unit) {
            repo.createBloodBank(userID, callback)

    }

    fun getDataFromDB(userID: String, callback: (BloodBankModel?, Boolean, String) -> Unit) {

            repo.getDataFromDB(userID, callback)

    }

    fun editBloods(userID: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit) {

            repo.editBloods(userID, data, callback)

    }

    fun searchBlood(bloodGroup: String) {

            repo.searchBlood(bloodGroup) { data, success, message ->
                if (success) {
                    _bloodList.postValue(data)
                } else {
                    _errorMessage.postValue(message)

            }
        }
    }
}
