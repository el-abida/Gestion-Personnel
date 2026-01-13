package com.ensa.gestionpersonnel.ui.absence.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ensa.gestionpersonnel.R
import com.ensa.gestionpersonnel.databinding.ItemAbsenceBinding
import com.ensa.gestionpersonnel.domain.model.Absence
import com.ensa.gestionpersonnel.domain.model.AbsenceType
import java.text.SimpleDateFormat
import java.util.Locale

class AbsenceAdapter(
    private val onItemClick: (Absence) -> Unit,
    private val onValidateClick: (Absence) -> Unit,
    private val onDeleteClick: (Absence) -> Unit
) : ListAdapter<Absence, AbsenceAdapter.AbsenceViewHolder>(AbsenceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsenceViewHolder {
        val binding = ItemAbsenceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AbsenceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AbsenceViewHolder, position: Int) {
        val absence = getItem(position)
        holder.bind(absence)
    }

    inner class AbsenceViewHolder(
        private val binding: ItemAbsenceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }

            binding.btnValidate.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onValidateClick(getItem(position))
                }
            }

            binding.btnDelete.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onDeleteClick(getItem(position))
                }
            }
        }

        fun bind(absence: Absence) {
            binding.apply {
                tvPersonnelName.text = "${absence.personnelPrenom} ${absence.personnelNom}"
                tvPpr.text = "PPR: ${absence.personnelPpr}"

                // Formater les dates
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
                val dateDebut = dateFormat.format(absence.dateDebut)
                val dateFin = dateFormat.format(absence.dateFin)
                tvPeriod.text = "Du $dateDebut au $dateFin"

                val duree = absence.getDureeJours()
                tvDuration.text = "$duree jours"

                // Type avec emoji
                when (absence.type) {
                    AbsenceType.CONGE_ANNUEL -> {
                        tvType.text = "Congé Annuel"
                        tvType.setChipBackgroundColorResource(R.color.blue_light)
                    }
                    AbsenceType.MALADIE -> {
                        tvType.text = "Maladie"
                        tvType.setChipBackgroundColorResource(R.color.red_light)
                    }
                    AbsenceType.EXCEPTIONNELLE -> {
                        tvType.text = "Exceptionnelle"
                        tvType.setChipBackgroundColorResource(R.color.orange_light)
                    }
                    AbsenceType.NON_JUSTIFIEE -> {
                        tvType.text = "Non Justifiée"
                        tvType.setChipBackgroundColorResource(R.color.gray_light)
                    }
                }

                tvMotif.text = absence.motif ?: "Aucun motif"

                if (absence.personnelSolde != null) {
                    tvSoldeConges.visibility = View.VISIBLE
                    tvSoldeConges.text = "Solde: ${absence.personnelSolde} j"
                    val color = when {
                        absence.personnelSolde < 5 -> android.R.color.holo_red_dark
                        absence.personnelSolde < 15 -> android.R.color.holo_orange_dark
                        else -> android.R.color.holo_green_dark
                    }
                    tvSoldeConges.setTextColor(root.context.getColor(color))
                } else {
                    tvSoldeConges.visibility = View.GONE
                }

                // Badge de statut
                if (absence.estValideeParAdmin) {
                    tvStatus.text = "Validée"
                    tvStatus.setBackgroundResource(R.drawable.bg_status_validated)
                    tvStatus.setTextColor(root.context.getColor(android.R.color.white))
                    btnValidate.visibility = View.GONE
                } else {
                    tvStatus.text = "En attente"
                    tvStatus.setBackgroundResource(R.drawable.bg_status_pending)
                    tvStatus.setTextColor(root.context.getColor(android.R.color.white))
                    btnValidate.visibility = View.VISIBLE
                    btnValidate.text = "Valider"
                }

                // Justificatif
                if (absence.justificatifUrl != null) {
                    tvJustificatif.visibility = View.VISIBLE
                    tvJustificatif.text = "Justificatif présent"
                } else {
                    tvJustificatif.visibility = View.GONE
                }

                // Information de déduction pour absences non justifiées VALIDÉES
                if (absence.type == AbsenceType.NON_JUSTIFIEE && absence.estValideeParAdmin) {
                    layoutPenalite.visibility = View.VISIBLE
                    tvPenaliteInfo.text = "Info: $duree jours déduits du solde"
                    layoutPenalite.setBackgroundColor(root.context.getColor(R.color.gray_light))
                    tvPenaliteInfo.setTextColor(root.context.getColor(R.color.gray_dark))
                } else {
                    layoutPenalite.visibility = View.GONE
                }
            }
        }

    }

    class AbsenceDiffCallback : DiffUtil.ItemCallback<Absence>() {
        override fun areItemsTheSame(oldItem: Absence, newItem: Absence): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Absence, newItem: Absence): Boolean {
            // Toujours retourner false pour forcer le refresh
            return false
        }
    }
}