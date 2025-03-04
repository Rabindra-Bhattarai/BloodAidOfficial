package com.example.blood_aid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.blood_aid.R
import com.example.blood_aid.model.OrganizationModel
import com.example.blood_aid.viewmodel.AdminViewModel
import com.example.blood_aid.viewmodel.BloodRepoViewModel
import com.example.blood_aid.viewmodel.UserViewModel

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
       holder.phoneNumber.text=organization.phoneNumber
        holder.requestButtoon.setOnClickListener{
            //
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
        val requestButtoon: Button= itemView.findViewById(R.id.buttonRequest)
    }
}
