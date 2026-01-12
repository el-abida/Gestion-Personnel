package com.ensa.gestionpersonnel.ui.avancement.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ensa.gestionpersonnel.databinding.FragmentAvancementListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AvancementListFragment : Fragment() {

    private var _binding: FragmentAvancementListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AvancementListViewModel by viewModels()
    private lateinit var adapter: AvancementAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAvancementListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupListeners()

        viewModel.loadAvancements()
    }

    private fun setupRecyclerView() {
        adapter = AvancementAdapter(
            onItemClick = { avancement ->
                val action = AvancementListFragmentDirections
                    .actionAvancementListFragmentToAvancementDetailFragment(avancement.id)
                findNavController().navigate(action)
            },
            onDeleteClick = { avancement ->
                showDeleteConfirmation(avancement.id)
            }
        )

        binding.recyclerViewAvancements.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@AvancementListFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.avancements.observe(viewLifecycleOwner) { avancements ->
            adapter.submitList(avancements)
            binding.emptyView.visibility = if (avancements.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Optionally show a progress indicator
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupListeners() {
        binding.fabAddAvancement.setOnClickListener {
            val action = AvancementListFragmentDirections.actionAvancementListFragmentToAvancementFormFragment(0L)
            findNavController().navigate(action)
        }

        binding.editTextSearch.addTextChangedListener { text ->
            viewModel.searchAvancements(text.toString())
        }
    }

    private fun showDeleteConfirmation(id: Long) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Supprimer l'avancement")
            .setMessage("Voulez-vous vraiment supprimer cet avancement ?")
            .setPositiveButton("Supprimer") { _, _ ->
                viewModel.deleteAvancement(id)
            }
            .setNegativeButton("Annuler", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}