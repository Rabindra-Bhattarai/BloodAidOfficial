package com.example.blood_aid.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blood_aid.R
import com.example.blood_aid.model.OrganizationModel

class OrgAdminAdapter(
    private val context: Context,
    private var orgList: List<OrganizationModel>,
    private val onCheckedChanged: (String, Boolean) -> Unit
) : RecyclerView.Adapter<OrgAdminAdapter.OrgViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_org, parent, false)
        return OrgViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orgList.size
    }

    override fun onBindViewHolder(holder: OrgViewHolder, position: Int) {
        val organization = orgList[position]
        holder.name.text = organization.fullName
        holder.address.text = organization.address
        holder.email.text = organization.email
        holder.registrationNo.text = organization.registrationNumber
        holder.enabled.isChecked = organization.enabled
        holder.enabled.setOnCheckedChangeListener(null) // Avoid unwanted triggers during recycling
        holder.enabled.setOnCheckedChangeListener { _, isChecked ->
            organization.enabled = isChecked
            onCheckedChanged(organization.userId, isChecked)
        }

        Log.d("OrgAdminAdapter", "Bound organization: ${organization.fullName}")
    }

    fun updateData(newOrgList: List<OrganizationModel>) {
        orgList = newOrgList
        notifyDataSetChanged()
        Log.d("OrgAdminAdapter", "Adapter data updated with ${newOrgList.size} organizations")
    }

    class OrgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.text_view_name_detail)
        val address: TextView = itemView.findViewById(R.id.text_view_address_detail)
        val email: TextView = itemView.findViewById(R.id.text_view_email_detail)
        val registrationNo: TextView = itemView.findViewById(R.id.text_view_registration_no_detail)
        val enabled: CheckBox = itemView.findViewById(R.id.checkBox)
    }
}
