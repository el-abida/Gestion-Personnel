package com.ensa.gestionpersonnel.data.remote.dto

import com.ensa.gestionpersonnel.domain.model.Diplome
import com.ensa.gestionpersonnel.domain.model.NiveauDiplome
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class DiplomeDto(
    @SerializedName("id")
    val id: Long = 0,
    
    @SerializedName("intitule")
    val intitule: String,
    
    @SerializedName("specialite")
    val specialite: String?,
    
    @SerializedName("niveau")
    val niveau: String?,
    
    @SerializedName("etablissement")
    val etablissement: String?,
    
    @SerializedName("dateObtention")
    val dateObtention: String?,
    
    @SerializedName("fichierPreuve")
    val fichierPreuve: String?,
    
    @SerializedName("personnelId")
    val personnelId: Long,
    
    @SerializedName("personnelNom")
    val personnelNom: String? = null,
    
    @SerializedName("personnelPrenom")
    val personnelPrenom: String? = null
) {
    fun toDomain(): Diplome {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return Diplome(
            id = id,
            intitule = intitule,
            specialite = specialite ?: "",
            niveau = try {
                if (niveau != null) NiveauDiplome.valueOf(niveau) else NiveauDiplome.Bac
            } catch (e: Exception) { NiveauDiplome.Bac },
            etablissement = etablissement ?: "",
            dateObtention = try {
                if (dateObtention != null) dateFormat.parse(dateObtention) else null
            } catch (e: Exception) { null },
            fichierPreuve = fichierPreuve,
            personnelId = personnelId,
            personnelNom = personnelNom,
            personnelPrenom = personnelPrenom
        )
    }

    companion object {
        fun fromDomain(diplome: Diplome): DiplomeDto {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return DiplomeDto(
                id = diplome.id,
                intitule = diplome.intitule,
                specialite = diplome.specialite,
                niveau = diplome.niveau.name,
                etablissement = diplome.etablissement,
                dateObtention = if (diplome.dateObtention != null) dateFormat.format(diplome.dateObtention) else null,
                fichierPreuve = diplome.fichierPreuve,
                personnelId = diplome.personnelId,
                personnelNom = diplome.personnelNom,
                personnelPrenom = diplome.personnelPrenom
            )
        }
    }
}
