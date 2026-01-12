package com.ensa.gestionpersonnel.ui.absence.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ensa.gestionpersonnel.databinding.FragmentAbsenceListBinding
import com.ensa.gestionpersonnel.domain.model.Absence
import com.ensa.gestionpersonnel.domain.model.AbsenceType
import com.ensa.gestionpersonnel.ui.absence.AbsenceViewModel
import com.ensa.gestionpersonnel.utils.NetworkResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AbsenceListFragment : Fragment() {

    private var _binding: FragmentAbsenceListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AbsenceViewModel by viewModels()
    private lateinit var absenceAdapter: AbsenceAdapter
    private var currentAbsenceType: AbsenceType? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAbsenceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupérer le type d'absence depuis les arguments
        arguments?.getString("absenceType")?.let { typeString ->
            if (typeString.isNotEmpty()) {
                currentAbsenceType = AbsenceType.valueOf(typeString)
                // Restreindre immédiatement le ViewModel à ce type
                viewModel.filterAbsencesByType(typeString)
            }
        }

        setupUI()
        setupRecyclerView()
        setupObservers()
        setupSearch()

        // Charger les données (applyFilters sera appelé automatiquement à la fin du chargement)
        loadInitialData()
    }

    override fun onResume() {
        super.onResume()
        // Recharger les données à chaque fois qu'on revient sur cet écran
        loadInitialData()
    }

    private fun loadInitialData() {
        val personnelId = arguments?.getLong("personnelId", 0L) ?: 0L

        if (personnelId != 0L) {
            // Charger les absences d'un personnel spécifique
            viewModel.loadAbsencesByPersonnel(personnelId)
        } else {
            // Charger toutes les absences
            viewModel.loadAllAbsences()
        }
    }

    private fun setupUI() {
        // Configuration du sous-titre selon le type
        binding.tvListSubtitle.text = when (currentAbsenceType) {
            AbsenceType.CONGE_ANNUEL -> "Congés annuels"
            AbsenceType.MALADIE -> "Absences Maladie"
            AbsenceType.EXCEPTIONNELLE -> "Absences Exceptionnelles"
            AbsenceType.NON_JUSTIFIEE -> "Absences Non Justifiées"
            null -> "Toutes les Absences"
        }

        // Bouton d'ajout - passe le type d'absence
        binding.fabAddAbsence.setOnClickListener {
            val personnelId = arguments?.getLong("personnelId", 0L) ?: 0L
            val action = AbsenceListFragmentDirections.actionAbsenceListToAbsenceForm(
                personnelId = personnelId,
                absenceType = currentAbsenceType?.name ?: ""
            )
            findNavController().navigate(action)
        }

        // Masquer le spinner de filtre si on a déjà un type
        if (currentAbsenceType != null) {
            binding.spinnerFilterType.visibility = View.GONE
            binding.btnFilter.visibility = View.GONE
        }

        binding.btnClearFilter.setOnClickListener {
            viewModel.clearFilters()
            // Réappliquer le filtre de type si on est dans une vue filtrée
            currentAbsenceType?.let { type ->
                viewModel.filterAbsencesByType(type.name)
            }
            binding.btnClearFilter.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        absenceAdapter = AbsenceAdapter(
            onItemClick = { absence: Absence ->
                absence.id?.let { id ->
                    val action = AbsenceListFragmentDirections
                        .actionAbsenceListToAbsenceDetail(id)
                    findNavController().navigate(action)
                }
            },
            onValidateClick = { absence: Absence ->
                showValidationDialog(absence)
            },
            onDeleteClick = { absence: Absence ->
                showDeleteConfirmation(absence)
            }
        )

        binding.recyclerViewAbsences.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = absenceAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        // Observer le chargement
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observer les absences filtrées (inclut la recherche)
        viewModel.filteredAbsences.observe(viewLifecycleOwner) { filteredList ->
            absenceAdapter.submitList(filteredList.toList())
            
            binding.tvEmptyState.visibility = if (filteredList.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
            updateStatistics(filteredList)
        }

        // Observer les erreurs depuis le résultat brut si besoin
        viewModel.absences.observe(viewLifecycleOwner) { result ->
            if (result is NetworkResult.Error) {
                showErrorDialog(result.message ?: "Erreur inconnue")
            }
        }

        // Observer les opérations sur les absences
        viewModel.absenceOperation.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    // Handled by viewModel.isLoading
                }
                is NetworkResult.Success -> {
                    Snackbar.make(
                        binding.root,
                        "Opération réussie",
                        Snackbar.LENGTH_SHORT
                    ).show()

                    // Recharger après un court délai
                    Handler(Looper.getMainLooper()).postDelayed({
                        loadInitialData()
                    }, 300)
                }
                is NetworkResult.Error -> {
                    // Handled by viewModel.isLoading for progress bar
                    showErrorDialog(result.message ?: "Erreur inconnue")
                }
                else -> {}
            }
        }
    }

    private fun updateStatistics(absences: List<Absence>) {
        val total = absences.size
        val pending = absences.count { !it.estValideeParAdmin }

        binding.tvTotalAbsences.text = total.toString()
        binding.tvPendingAbsences.text = pending.toString()
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchAbsences(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })
    }

    private fun showValidationDialog(absence: Absence) {
        if (absence.estValideeParAdmin) {
            Snackbar.make(binding.root, "Cette absence est déjà validée", Snackbar.LENGTH_SHORT).show()
            return
        }

        val action = "Valider"
        val message = "Voulez-vous valider cette absence de " +
                "${absence.personnelPrenom} ${absence.personnelNom} ?\n" +
                "Cette action déduira également le solde de congés si applicable."

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("$action l'absence")
            .setMessage(message)
            .setPositiveButton(action) { _, _ ->
                absence.id?.let { id ->
                    viewModel.validateAbsence(id)
                }
            }
            .setNegativeButton("Annuler", null)
            .show()
    }

    private fun showDeleteConfirmation(absence: Absence) {
        val message = "Voulez-vous vraiment supprimer cette absence de " +
                "${absence.personnelPrenom} ${absence.personnelNom} ?"

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Supprimer l'absence")
            .setMessage(message)
            .setPositiveButton("Supprimer") { _, _ ->
                absence.id?.let { id ->
                    viewModel.deleteAbsence(id)
                }
            }
            .setNegativeButton("Annuler", null)
            .show()
    }

    private fun showErrorDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Erreur")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}