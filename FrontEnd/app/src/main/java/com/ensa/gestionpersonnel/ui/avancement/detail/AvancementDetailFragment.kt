package com.ensa.gestionpersonnel.ui.avancement.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ensa.gestionpersonnel.R
import com.ensa.gestionpersonnel.databinding.FragmentAvancementDetailBinding
import com.ensa.gestionpersonnel.utils.NetworkResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AvancementDetailFragment : Fragment() {

    private var _binding: FragmentAvancementDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AvancementDetailViewModel by viewModels()
    private val args: AvancementDetailFragmentArgs by navArgs()

    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAvancementDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadAvancement(args.avancementId)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.avancement.observe(viewLifecycleOwner) { avancement ->
            binding.apply {
                tvGradeNouveau.text = avancement.gradeNouveau
                tvSummary.text = "Passage depuis : ${avancement.gradePrecedent}"
                tvGradePrecedent.text = avancement.gradePrecedent
                tvEchelleEchelonPrecedent.text = "Echelle ${avancement.echellePrecedente} • Echelon ${avancement.echelonPrecedent}"
                
                tvGradeNouveauDetail.text = avancement.gradeNouveau
                tvEchelleEchelonNouveau.text = "Echelle ${avancement.echelleNouvelle} • Echelon ${avancement.echelonNouveau}"
                
                tvDateDecision.text = dateFormat.format(avancement.dateDecision)
                tvDateEffet.text = dateFormat.format(avancement.dateEffet)
                
                val personnelName = if (avancement.personnelNom != null && avancement.personnelPrenom != null) {
                    "${avancement.personnelPrenom} ${avancement.personnelNom}"
                } else {
                    "Personnel #${avancement.personnelId}"
                }
                tvPersonnel.text = personnelName
                
                tvDescription.text = if (avancement.description.isBlank()) "Aucune description" else avancement.description
            }
        }

        viewModel.deleteState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    binding.btnDelete.isEnabled = false
                }
                is NetworkResult.Success -> {
                    Toast.makeText(context, "Avancement supprimé", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                is NetworkResult.Error -> {
                    binding.btnDelete.isEnabled = true
                    Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnEdit.setOnClickListener {
            val action = AvancementDetailFragmentDirections.actionAvancementDetailToAvancementForm(args.avancementId)
            findNavController().navigate(action)
        }

        binding.btnDelete.setOnClickListener {
            showDeleteConfirmation()
        }
    }

    private fun showDeleteConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Supprimer l'avancement")
            .setMessage("Êtes-vous sûr de vouloir supprimer cet avancement ? Cette action est irréversible.")
            .setPositiveButton("Supprimer") { _, _ ->
                viewModel.deleteAvancement(args.avancementId)
            }
            .setNegativeButton("Annuler", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
