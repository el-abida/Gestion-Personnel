package com.ensa.gestionpersonnel.data.remote.dto

/**
 * Réponse de l'API d'authentification.
 * Adaptée au modèle métier du backend Spring Boot.
 */
data class LoginResponse(
    val id: Long,
    val nom: String,
    val prenom: String,
    val email: String,
    val username: String,
    val token: String
)
