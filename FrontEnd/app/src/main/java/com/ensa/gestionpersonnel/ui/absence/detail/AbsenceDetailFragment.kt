package com.ensa.gestionpersonnel.ui.absence.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ensa.gestionpersonnel.R
import com.ensa.gestionpersonnel.databinding.FragmentAbsenceDetailBinding
import com.ensa.gestionpersonnel.domain.model.Absence
import com.ensa.gestionpersonnel.domain.model.AbsenceType
import com.ensa.gestionpersonnel.ui.absence.AbsenceViewModel
import com.ensa.gestionpersonnel.utils.NetworkResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class AbsenceDetailFragment : Fragment() {

    private var _binding: FragmentAbsenceDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AbsenceViewModel by viewModels()
    private var absenceId: Long = 0L
    private var currentAbsence: Absence? = null
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAbsenceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        absenceId = arguments?.getLong("absenceId") ?: 0L

        setupUI()
        setupObservers()
        
        if (absenceId != 0L) {
            viewModel.loadAllAbsences() // Note: In a real app, loadById would be better
        }
    }

    private fun setupUI() {


        binding.btnDelete.setOnClickListener {
            showDeleteConfirmation()
        }

        binding.btnValidate.setOnClickListener {
            showValidationDialog()
        }
    }

    private fun setupObservers() {
        // Observer la liste pour trouver l'absence spécifique
        viewModel.absences.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Loading -> binding.progressBar.visibility = View.VISIBLE
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val absence = result.data?.find { it.id == absenceId }
                    if (absence != null) {
                        displayAbsenceDetails(absence)
                    } else {
                        Snackbar.make(binding.root, "Absence non trouvée", Snackbar.LENGTH_LONG).show()
                        findNavController().navigateUp()
                    }
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(binding.root, result.message ?: "Erreur", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        // Observer les opérations
        viewModel.absenceOperation.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Loading -> binding.progressBar.visibility = View.VISIBLE
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(binding.root, "Opération réussie", Snackbar.LENGTH_SHORT).show()
                    viewModel.loadAllAbsences() // Refresh
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Erreur")
                        .setMessage(result.message)
                        .setPositiveButton("OK", null)
                        .show()
                }
                else -> {}
            }
        }
    }

    private fun displayAbsenceDetails(absence: Absence) {
        currentAbsence = absence
        binding.tvPersonnelNom.text = "${absence.personnelPrenom} ${absence.personnelNom}"
        binding.tvPersonnelPpr.text = "PPR: ${absence.personnelPpr}"
        
        binding.tvType.text = when (absence.type) {
            AbsenceType.CONGE_ANNUEL -> "Congé Annuel"
            AbsenceType.MALADIE -> "Absence Maladie"
            AbsenceType.EXCEPTIONNELLE -> "Absence Exceptionnelle"
            AbsenceType.NON_JUSTIFIEE -> "Absence Non Justifiée"
        }

        binding.tvPeriode.text = "Du ${dateFormat.format(absence.dateDebut)} au ${dateFormat.format(absence.dateFin)}"
        
        val diff = absence.dateFin.time - absence.dateDebut.time
        val days = (diff / (24 * 60 * 60 * 1000)).toInt() + 1
        binding.tvDuree.text = "$days jour(s)"
        
        binding.tvMotif.text = absence.motif ?: "Aucun motif spécifié"

        if (absence.estValideeParAdmin) {
            binding.tvStatut.text = "VALIDÉE"
            binding.tvStatut.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.holo_green_dark))
            binding.btnValidate.visibility = View.GONE
        } else {
            binding.tvStatut.text = "EN ATTENTE"
            binding.tvStatut.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.holo_orange_dark))
            binding.btnValidate.visibility = View.VISIBLE
        }
    }

    private fun showValidationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Valider l'absence")
            .setMessage("Voulez-vous valider cette absence ? Cette action est irréversible et déduira le solde si applicable.")
            .setPositiveButton("Valider") { _, _ ->
                viewModel.validateAbsence(absenceId)
            }
            .setNegativeButton("Annuler", null)
            .show()
    }

    private fun showDeleteConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Supprimer l'absence")
            .setMessage("Êtes-vous sûr de vouloir supprimer cette absence ?")
            .setPositiveButton("Supprimer") { _, _ ->
                viewModel.deleteAbsence(absenceId)
                findNavController().navigateUp()
            }
            .setNegativeButton("Annuler", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}