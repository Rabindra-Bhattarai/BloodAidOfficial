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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blood_aid.adapter.OrgAdminAdapter
import com.example.blood_aid.databinding.FragmentOrganizationBinding
import com.example.blood_aid.repository.AdminRepositoryImpl
import com.example.blood_aid.viewmodel.AdminViewModel

class OrganizationFragment : Fragment() {

    private lateinit var binding: FragmentOrganizationBinding
    private lateinit var adminViewModel: AdminViewModel
    private lateinit var orgAdminAdapter: OrgAdminAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrganizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adminViewModel = AdminViewModel(AdminRepositoryImpl())
        setupRecyclerView()
        observeViewModel()
        fetchOrganizations()
    }

    private fun setupRecyclerView() {
        orgAdminAdapter = OrgAdminAdapter(requireContext(), arrayListOf(), adminViewModel)
        binding.orgsAdmnRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.orgsAdmnRecycler.adapter = orgAdminAdapter
    }

    private fun observeViewModel() {
        adminViewModel.allOrg.observe(viewLifecycleOwner) { product ->
            product?.let {
                orgAdminAdapter.updateData(it)
            }
        }

        adminViewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            Toast.makeText(requireContext(), if (isLoading) "Loading..." else "Loaded", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchOrganizations() {
        adminViewModel.fetchOrganizations()
    }

    private fun retryFetchingOrganizations() {
        Handler(Looper.getMainLooper()).postDelayed({
            fetchOrganizations()
        }, 10000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
