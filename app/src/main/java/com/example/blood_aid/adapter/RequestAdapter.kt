package com.example.blood_aid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blood_aid.R
import com.example.blood_aid.model.RequestModel

class RequestAdapter(private var requestList: MutableList<RequestModel>) : RecyclerView.Adapter<RequestAdapter.RequestViewHolder>() {

    class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val requestorNameTextView: TextView = itemView.findViewById(R.id.requestor_name)
        private val phoneNumberTextView: TextView = itemView.findViewById(R.id.phone_number)
        private val bloodGroupTextView: TextView = itemView.findViewById(R.id.blood_group)
        private val addressTextView: TextView = itemView.findViewById(R.id.address)

        fun bind(request: RequestModel) {
            requestorNameTextView.text = request.requestorName
            phoneNumberTextView.text = request.phoneNumber
            bloodGroupTextView.text = request.bloodGroup
            addressTextView.text = request.address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_request_item, parent, false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.bind(requestList[position])
    }

    override fun getItemCount(): Int {
        return requestList.size
    }

    fun updateRequests(newRequestList: MutableList<RequestModel>) {
        requestList = newRequestList
        notifyDataSetChanged()
    }
}