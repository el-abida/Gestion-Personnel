package com.ensa.gestionpersonnel.data.repository

import com.ensa.gestionpersonnel.data.local.PreferencesManager
import com.ensa.gestionpersonnel.data.remote.api.ProfileApi
import com.ensa.gestionpersonnel.domain.model.ResponsableRH
import com.ensa.gestionpersonnel.utils.NetworkResult
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileApi: ProfileApi,
    private val preferencesManager: PreferencesManager
) {
    suspend fun getProfile(id: Long): NetworkResult<ResponsableRH> {
        return try {
            val response = profileApi.getProfile(id)
            if (response.isSuccessful && response.body() != null) {
                val profile = response.body()!!
                preferencesManager.saveRhProfile(profile)
                NetworkResult.Success(profile)
            } else {
                NetworkResult.Error("Erreur lors de la récupération du profil")
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Erreur réseau")
        }
    }

    suspend fun updateProfile(id: Long, profile: ResponsableRH): NetworkResult<ResponsableRH> {
        return try {
            val response = profileApi.updateProfile(id, profile)
            if (response.isSuccessful && response.body() != null) {
                val updatedProfile = response.body()!!
                preferencesManager.saveRhProfile(updatedProfile)
                NetworkResult.Success(updatedProfile)
            } else {
                NetworkResult.Error("Erreur lors de la mise à jour du profil")
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Erreur réseau")
        }
    }
}
