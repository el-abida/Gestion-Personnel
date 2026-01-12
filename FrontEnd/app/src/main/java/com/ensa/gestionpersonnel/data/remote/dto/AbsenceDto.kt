package com.ensa.gestionpersonnel.data.remote.dto

import com.ensa.gestionpersonnel.domain.model.Absence
import com.ensa.gestionpersonnel.domain.model.AbsenceType
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class AbsenceDto(
    @SerializedName("id") val id: Long?,
    @SerializedName("dateDebut") val dateDebut: String,
    @SerializedName("dateFin") val dateFin: String,
    @SerializedName("type") val type: String,
    @SerializedName("motif") val motif: String?,
    @SerializedName("justificatifUrl") val justificatifUrl: String?,
    @SerializedName("estValideeParAdmin") val estValideeParAdmin: Boolean,
    @SerializedName("personnelId") val personnelId: Long,
    @SerializedName("personnelNom") val personnelNom: String?,
    @SerializedName("personnelPrenom") val personnelPrenom: String?,
    @SerializedName("personnelPpr") val personnelPpr: String?,
    @SerializedName("personnelSolde") val personnelSolde: Int?
) {
    fun toDomain(): Absence {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        
        // Normalisation simple : minuscules, suppression des espaces, tirets et accents courants
        val normalizedType = type.lowercase()
            .replace("é", "e")
            .replace("è", "e")
            .replace("_", "")
            .replace(" ", "")

        val mappedType = when (normalizedType) {
            "maladie" -> AbsenceType.MALADIE
            "congeannuel" -> AbsenceType.CONGE_ANNUEL
            "exceptionnelle" -> AbsenceType.EXCEPTIONNELLE
            "nonjustifiee" -> AbsenceType.NON_JUSTIFIEE
            else -> {
                // Test exact case if raw mapping fails
                try {
                    AbsenceType.valueOf(type.uppercase())
                } catch (e: Exception) {
                    AbsenceType.NON_JUSTIFIEE
                }
            }
        }

        return Absence(
            id = id ?: 0L,
            dateDebut = try { dateFormat.parse(dateDebut) ?: Date() } catch (e: Exception) { Date() },
            dateFin = try { dateFormat.parse(dateFin) ?: Date() } catch (e: Exception) { Date() },
            type = mappedType,
            motif = motif ?: "",
            justificatifUrl = justificatifUrl,
            estValideeParAdmin = estValideeParAdmin,
            personnelId = personnelId,
            personnelNom = personnelNom,
            personnelPrenom = personnelPrenom,
            personnelPpr = personnelPpr,
            personnelSolde = personnelSolde
        )
    }

    companion object {
        fun fromDomain(absence: Absence): AbsenceDto {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            
            val apiType = when (absence.type) {
                AbsenceType.MALADIE -> "Maladie"
                AbsenceType.CONGE_ANNUEL -> "Congé_Annuel"
                AbsenceType.EXCEPTIONNELLE -> "Exceptionnelle"
                AbsenceType.NON_JUSTIFIEE -> "Non_Justifiée"
            }

            return AbsenceDto(
                id = if (absence.id == 0L) null else absence.id,
                dateDebut = dateFormat.format(absence.dateDebut),
                dateFin = dateFormat.format(absence.dateFin),
                type = apiType,
                motif = absence.motif,
                justificatifUrl = absence.justificatifUrl,
                estValideeParAdmin = absence.estValideeParAdmin,
                personnelId = absence.personnelId,
                personnelNom = absence.personnelNom,
                personnelPrenom = absence.personnelPrenom,
                personnelPpr = absence.personnelPpr,
                personnelSolde = absence.personnelSolde
            )
        }
    }
}