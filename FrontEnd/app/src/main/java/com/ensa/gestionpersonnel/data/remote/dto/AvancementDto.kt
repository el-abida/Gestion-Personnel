package com.ensa.gestionpersonnel.data.remote.dto

import com.ensa.gestionpersonnel.domain.model.Avancement
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class AvancementDto(
    @SerializedName("id") val id: Long?,
    @SerializedName("dateDecision") val dateDecision: String,
    @SerializedName("dateEffet") val dateEffet: String,
    @SerializedName("gradePrecedent") val gradePrecedent: String?,
    @SerializedName("gradeNouveau") val gradeNouveau: String,
    @SerializedName("echellePrecedente") val echellePrecedente: Int?,
    @SerializedName("echelleNouvelle") val echelleNouvelle: Int?,
    @SerializedName("echelonPrecedent") val echelonPrecedent: Int?,
    @SerializedName("echelonNouveau") val echelonNouveau: Int?,
    @SerializedName("description") val description: String?,
    @SerializedName("personnelId") val personnelId: Long,
    @SerializedName("personnelNom") val personnelNom: String?,
    @SerializedName("personnelPrenom") val personnelPrenom: String?
) {
    fun toDomain(): Avancement {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return Avancement(
            id = id ?: 0L,
            personnelId = personnelId,
            personnelNom = personnelNom,
            personnelPrenom = personnelPrenom,
            dateDecision = try { dateFormat.parse(dateDecision) ?: Date() } catch (e: Exception) { Date() },
            dateEffet = try { dateFormat.parse(dateEffet) ?: Date() } catch (e: Exception) { Date() },
            gradePrecedent = gradePrecedent ?: "",
            gradeNouveau = gradeNouveau,
            echellePrecedente = echellePrecedente ?: 0,
            echelleNouvelle = echelleNouvelle ?: 0,
            echelonPrecedent = echelonPrecedent ?: 0,
            echelonNouveau = echelonNouveau ?: 0,
            description = description ?: ""
        )
    }

    companion object {
        fun fromDomain(avancement: Avancement): AvancementDto {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return AvancementDto(
                id = if (avancement.id == 0L) null else avancement.id,
                personnelId = avancement.personnelId,
                personnelNom = avancement.personnelNom,
                personnelPrenom = avancement.personnelPrenom,
                dateDecision = dateFormat.format(avancement.dateDecision),
                dateEffet = dateFormat.format(avancement.dateEffet),
                gradePrecedent = avancement.gradePrecedent,
                gradeNouveau = avancement.gradeNouveau,
                echellePrecedente = avancement.echellePrecedente,
                echelleNouvelle = avancement.echelleNouvelle,
                echelonPrecedent = avancement.echelonPrecedent,
                echelonNouveau = avancement.echelonNouveau,
                description = avancement.description
            )
        }
    }
}
