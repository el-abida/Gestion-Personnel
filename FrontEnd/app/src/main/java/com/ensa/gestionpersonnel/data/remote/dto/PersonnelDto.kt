package com.ensa.gestionpersonnel.data.remote.dto

data class PersonnelDto(
    val id: Long? = null,
    val ppr: String,
    val citoyen: CitoyenDto,
    val typeEmploye: String,
    val dateRecrutementMinistere: String,
    val dateRecrutementEnsa: String, // Corrected casing from ENSA to Ensa
    val gradeActuel: String,
    val echelleActuelle: Int,
    val echelonActuel: Int,
    val soldeConges: Int = 30,
    val estActif: Boolean = true
)
