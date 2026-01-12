package com.ensa.gestionpersonnel.domain.model

import java.util.Date

/**
 * Énumération des niveaux de diplômes
 */
enum class NiveauDiplome {
    Bac,
    Bac_Plus_2,
    Licence,
    Master,
    Ingénieur,
    Doctorat
}

/**
 * Modèle représentant un diplôme d'un personnel
 */
data class Diplome(
    val id: Long = 0L,
    val intitule: String,
    val personnelId: Long,
    val specialite: String,
    val niveau: NiveauDiplome,
    val etablissement: String,
    val dateObtention: Date?,
    val fichierPreuve: String? = null,
    val personnelNom: String? = null,
    val personnelPrenom: String? = null
) {

    /**
     * Vérifie si le diplôme est valide
     * - L'intitulé ne doit pas être vide
     * - La date d'obtention ne doit pas être dans le futur
     */
    fun estValide(): Boolean {
        return intitule.isNotBlank() &&
                specialite.isNotBlank() &&
                etablissement.isNotBlank() &&
                (dateObtention?.before(Date()) ?: true)
    }

    /**
     * Retourne le nom complet du diplôme
     */
    fun getNomComplet(): String {
        return "$intitule - $specialite ($niveau)"
    }

    /**
     * Calcule l'ancienneté du diplôme en années
     */
    fun getAncienneteAnnees(): Int {
        if (dateObtention == null) return 0
        val diff = Date().time - dateObtention.time
        return (diff / (1000L * 60 * 60 * 24 * 365)).toInt()
    }

    /**
     * Vérifie si un fichier justificatif est attaché
     */
    fun hasPreuve(): Boolean = !fichierPreuve.isNullOrBlank()

    /**
     * Retourne le niveau numérique (utile pour les comparaisons)
     */
    fun getNiveauNumerique(): Int {
        return when (niveau) {
            NiveauDiplome.Bac -> 1
            NiveauDiplome.Bac_Plus_2 -> 2
            NiveauDiplome.Licence -> 3
            NiveauDiplome.Master -> 4
            NiveauDiplome.Ingénieur -> 4
            NiveauDiplome.Doctorat -> 5
        }
    }
}