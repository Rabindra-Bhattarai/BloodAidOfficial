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
import com.example.blood_aid.model.RequestModel
import com.example.blood_aid.viewmodel.AdminViewModel

class RequestAdapter(
    private val context: Context,
    private var orgList: ArrayList<RequestModel>,
) : RecyclerView.Adapter<RequestAdapter.RequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_request_item, parent, false)
        return RequestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orgList.size
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val organization = orgList[position]
        holder.name.text=organization.requestorName
        holder.number.text=organization.phoneNumber
        holder.bloodGroup.text=organization.bloodGroup
        holder.address.text=organization.address

    }

    fun updateData(newOrgList: List<RequestModel>?) {
        orgList.clear()
        if (newOrgList != null) {
            orgList.addAll(newOrgList)
        }
        notifyDataSetChanged()
    }

    class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.requestor_name)
        val number: TextView = itemView.findViewById(R.id.phone_number)
        val bloodGroup: TextView = itemView.findViewById(R.id.blood_group)
        val address: TextView = itemView.findViewById(R.id.address)
    }
}
