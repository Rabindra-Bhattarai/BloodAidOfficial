package com.example.blood_aid.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blood_aid.adapter.OrgAdminAdapter
import com.example.blood_aid.databinding.FragmentOrganizationBinding
import com.example.blood_aid.model.OrganizationModel
import com.example.blood_aid.repository.AdminRepositoryImpl
import com.example.blood_aid.viewmodel.AdminViewModel

class OrganizationFragment : Fragment() {

    private var _binding: FragmentOrganizationBinding? = null
    private val binding get() = _binding!!
    private lateinit var adminViewModel: AdminViewModel
    private lateinit var orgAdminAdapter: OrgAdminAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrganizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adminViewModel = AdminViewModel(AdminRepositoryImpl())

        setupRecyclerView()
        fetchOrganizations()
    }

    private fun setupRecyclerView() {
        orgAdminAdapter = OrgAdminAdapter(requireContext(), emptyList()) { userId, isEnabled ->
            adminViewModel.updateOrganization(userId, isEnabled) { success, message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
        binding.orgsAdmnRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.orgsAdmnRecycler.adapter = orgAdminAdapter
    }

    private fun fetchOrganizations() {
        adminViewModel.fetchOrganizations { orgList, success, message ->
            if (success) {
                Log.d("OrganizationFragment", "Fetched ${orgList.size} organizations")
                orgAdminAdapter.updateData(orgList)
            } else {
                Log.e("OrganizationFragment", "Error fetching organizations: $message")
                Toast.makeText(
                    requireContext(),
                    "Error fetching data. Retrying in 10 seconds.",
                    Toast.LENGTH_LONG
                ).show()
                retryFetchingOrganizations()
            }
        }
    }

    private fun retryFetchingOrganizations() {
        Handler(Looper.getMainLooper()).postDelayed({
            fetchOrganizations()
        }, 10000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
