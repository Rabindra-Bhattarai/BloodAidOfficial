package com.example.blood_aid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.blood_aid.R
import com.example.blood_aid.model.IndividualModel
import com.example.blood_aid.model.OrganizationModel
import com.example.blood_aid.repository.IndividualRepositoryImpl
import com.example.blood_aid.repository.RequestRepositoryImpl
import com.example.blood_aid.repository.UserRepositoryImpl
import com.example.blood_aid.viewmodel.BloodRepoViewModel
import com.example.blood_aid.viewmodel.IndividualViewModel
import com.example.blood_aid.viewmodel.RequestsViewModel
import com.example.blood_aid.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface UserFetchListener {
    fun onUserFetched(user: IndividualModel?)
}

class BloodBankAdapter(
    private val context: Context,
    private var orgList: ArrayList<OrganizationModel>,
    private val viewModel: BloodRepoViewModel
) : RecyclerView.Adapter<BloodBankAdapter.BloodBankViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BloodBankViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_blood_item, parent, false)
        return BloodBankViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orgList.size
    }

    override fun onBindViewHolder(holder: BloodBankViewHolder, position: Int) {
        val organization = orgList[position]
        holder.name.text = organization.fullName
        holder.address.text = organization.address
        holder.email.text = organization.email
        holder.phoneNumber.text = organization.phoneNumber

        holder.requestButton.setOnClickListener {
            val userViewModel = UserViewModel(UserRepositoryImpl())
            val requestsViewModel = RequestsViewModel(RequestRepositoryImpl())
            val individualViewModel = IndividualViewModel(IndividualRepositoryImpl())

            // Fetch user data and observe changes
            fetchUserData(userViewModel.getCurrentUser()?.uid.toString(), individualViewModel, object : UserFetchListener {
                override fun onUserFetched(user: IndividualModel?) {
                    user?.let {
                        CoroutineScope(Dispatchers.Main).launch {
                            val result = withContext(Dispatchers.IO) {
                                try {
                                    requestsViewModel.addRequest(it.fullName, it.phoneNumber, it.bloodGroup, it.address)
                                    "Request added successfully"
                                } catch (e: Exception) {
                                    "Failed to add request: ${e.message}"
                                }
                            }
                            Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                        }
                    } ?: run {
                        Toast.makeText(context, "User data not found", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun fetchUserData(userID: String, individualViewModel: IndividualViewModel, listener: UserFetchListener) {
        individualViewModel.getDataFromDB(userID)
        individualViewModel.userData.observeForever { user ->
            listener.onUserFetched(user)
        }
    }

    fun updateData(newOrgList: List<OrganizationModel>?) {
        orgList.clear()
        if (newOrgList != null) {
            orgList.addAll(newOrgList)
        }
        notifyDataSetChanged()
    }

    class BloodBankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textViewOrganization)
        val address: TextView = itemView.findViewById(R.id.textViewLocation)
        val email: TextView = itemView.findViewById(R.id.textViewEmail)
        val phoneNumber: TextView = itemView.findViewById(R.id.textViewPhone)
        val requestButton: Button = itemView.findViewById(R.id.buttonRequest)
    }
}
