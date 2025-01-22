package com.example.blood_aid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.blood_aid.R
import com.example.blood_aid.model.OrganizationModel
import com.example.blood_aid.viewmodel.AdminViewModel

class OrgAdminAdapter(
    private val context: Context,
    private var orgList: ArrayList<OrganizationModel>,
    private val viewModel: AdminViewModel
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

        // Avoid unwanted triggers during recycling
        holder.enabled.setOnCheckedChangeListener(null)

        // Set checked change listener with Toast messages and update call
        holder.enabled.setOnCheckedChangeListener { _, isChecked ->
            organization.enabled = isChecked
            viewModel.updateOrganization(organization.userId, isChecked) { success, message ->
                if (success) {
                    Toast.makeText(context, "Updated: ${organization.fullName}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Update failed: $message", Toast.LENGTH_SHORT).show()
                }
            }
            Toast.makeText(context, if (isChecked) "Checked" else "Unchecked", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateData(newOrgList: ArrayList<OrganizationModel>) {
        orgList.clear()
        orgList = newOrgList
        notifyDataSetChanged()
    }

    class OrgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.text_view_name_detail)
        val address: TextView = itemView.findViewById(R.id.text_view_address_detail)
        val email: TextView = itemView.findViewById(R.id.text_view_email_detail)
        val registrationNo: TextView = itemView.findViewById(R.id.text_view_registration_no_detail)
        val enabled: CheckBox = itemView.findViewById(R.id.checkBox)
    }
}
