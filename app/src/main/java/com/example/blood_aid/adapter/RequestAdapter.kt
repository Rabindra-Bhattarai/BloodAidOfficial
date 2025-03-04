package com.example.blood_aid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blood_aid.R
import com.example.blood_aid.model.RequestModel
import com.example.blood_aid.viewmodel.RequestsViewModel

class RequestAdapter(
    private val context: Context,
    private var requests: ArrayList<RequestModel>,
    requestViewModel: RequestsViewModel,
) : RecyclerView.Adapter<RequestAdapter.RequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_request_item, parent, false)
        return RequestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return requests.size
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = requests[position]
        holder.requestedBy.text = request.requestorName
        holder.phone.text = request.phoneNumber
        holder.bldgrp.text = request.bloodGroup
        holder.address.text = request.address

    }

    fun updateData(newRequestList: List<RequestModel>?) {
        requests.clear()
        newRequestList?.let { requests.addAll(it) }
        notifyDataSetChanged()
    }

    class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val requestedBy: TextView = itemView.findViewById(R.id.requestor_name)
        val phone: TextView = itemView.findViewById(R.id.phone_number)
        val bldgrp: TextView = itemView.findViewById(R.id.blood_group)
        val address: TextView = itemView.findViewById(R.id.address)
    }
}