package com.ensa.gestionpersonnel.ui.diplome.detail

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ensa.gestionpersonnel.databinding.FragmentDiplomeDetailBinding
import com.ensa.gestionpersonnel.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class DiplomeDetailFragment : Fragment() {

    private var _binding: FragmentDiplomeDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DiplomeDetailViewModel by viewModels()
    private val args: DiplomeDetailFragmentArgs by navArgs()

    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiplomeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupObservers()
        setupListeners()

        viewModel.loadDiplome(args.diplomeId)
    }

    private fun setupObservers() {
        viewModel.diplome.observe(viewLifecycleOwner) { diplome ->
            diplome?.let {
                binding.apply {
                    tvIntitule.text = it.intitule
                    tvSpecialite.text = it.specialite
                    tvNiveau.text = it.niveau.name.replace("_", "+")
                    tvEtablissement.text = it.etablissement
                    tvDateObtention.text = if (it.dateObtention != null) dateFormat.format(it.dateObtention) else "Date inconnue"
                    
                    val personnelName = if (it.personnelNom != null && it.personnelPrenom != null) {
                         "${it.personnelPrenom} ${it.personnelNom}"
                    } else {
                         "Personnel #${it.personnelId}"
                    }
                    tvPersonnel.text = personnelName
                    
                    if (it.hasPreuve()) {
                        tvFichierPreuve.text = it.fichierPreuve
                        tvFichierPreuve.visibility = View.VISIBLE
                        labelFichierPreuve.visibility = View.VISIBLE
                    } else {
                        tvFichierPreuve.visibility = View.GONE
                        labelFichierPreuve.visibility = View.GONE
                    }
                }
            }
        }

        viewModel.deleteState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NetworkResult.Success -> {
                    Toast.makeText(context, "Diplôme supprimé", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    // Show loading if needed
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnEdit.setOnClickListener {
            val action = DiplomeDetailFragmentDirections.actionDiplomeDetailToDiplomeForm(diplomeId = args.diplomeId)
            findNavController().navigate(action)
        }

        binding.btnDelete.setOnClickListener {
            showDeleteConfirmation()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Supprimer le diplôme")
            .setMessage("Êtes-vous sûr de vouloir supprimer ce diplôme ?")
            .setPositiveButton("Oui") { _, _ ->
                viewModel.deleteDiplome(args.diplomeId)
            }
            .setNegativeButton("Non", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
