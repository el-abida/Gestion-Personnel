package com.ensa.gestionpersonnel.data.remote.dto

data class CitoyenDto(
    val cin: String? = null,
    val nomFr: String? = null,
    val nomAr: String? = null,
    val prenomFr: String? = null,
    val prenomAr: String? = null,
    val adresse: String? = null,
    val email: String? = null,
    val telephone: String? = null,
    val dateNaissance: String? = null,
    val lieuNaissance: String? = null,
    val sexe: String? = null,
    val photoUrl: String? = null
)
