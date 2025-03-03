package com.example.blood_aid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.blood_aid.R
import com.example.blood_aid.model.BloodBankModel

class BloodBankAdapter : ListAdapter<BloodBankModel, BloodBankAdapter.BloodBankViewHolder>(
    BloodBankDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BloodBankViewHolder {
        // Inflate the item layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_blood_item, parent, false)
        return BloodBankViewHolder(view)
    }

    override fun onBindViewHolder(holder: BloodBankViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BloodBankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewOrgId: TextView = itemView.findViewById(R.id.textViewOrganization)
        // Add other TextViews or Views as needed

        fun bind(bloodBank: BloodBankModel) {
            // Bind the data to the views
            textViewOrgId.text = bloodBank.OrgId
            // Bind other data as needed
            // Example: textViewBloodCount.text = bloodBank.getBloodCount() // If you have a method to get blood count
        }
    }

    class BloodBankDiffCallback : DiffUtil.ItemCallback<BloodBankModel>() {
        override fun areItemsTheSame(oldItem: BloodBankModel, newItem: BloodBankModel): Boolean {
            return oldItem.OrgId == newItem.OrgId
        }

        override fun areContentsTheSame(oldItem: BloodBankModel, newItem: BloodBankModel): Boolean {
            return oldItem == newItem
        }
    }
}