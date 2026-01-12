package com.ensa.gestionpersonnel.data.repository

import com.ensa.gestionpersonnel.data.remote.api.AbsenceApi
import com.ensa.gestionpersonnel.data.remote.dto.AbsenceDto
import com.ensa.gestionpersonnel.domain.model.Absence
import com.ensa.gestionpersonnel.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AbsenceRepository @Inject constructor(
    private val absenceApi: AbsenceApi
) {

    fun getAllAbsences(): Flow<NetworkResult<List<Absence>>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = absenceApi.getAllAbsences()
            if (response.isSuccessful && response.body() != null) {
                val absences = response.body()!!.map { it.toDomain() }
                emit(NetworkResult.Success(absences))
            } else {
                emit(NetworkResult.Error("Erreur lors de la récupération des absences"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Erreur inconnue"))
        }
    }

    fun getAbsenceById(id: Long): Flow<NetworkResult<Absence>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = absenceApi.getAbsenceById(id)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!.toDomain()))
            } else {
                emit(NetworkResult.Error("Absence non trouvée"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Erreur inconnue"))
        }
    }

    fun getAbsencesByPersonnel(personnelId: Long): Flow<NetworkResult<List<Absence>>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = absenceApi.getAbsencesByPersonnelId(personnelId)
            if (response.isSuccessful && response.body() != null) {
                val absences = response.body()!!.map { it.toDomain() }
                emit(NetworkResult.Success(absences))
            } else {
                emit(NetworkResult.Error("Erreur lors de la récupération des absences du personnel"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Erreur inconnue"))
        }
    }

    fun createAbsence(absence: Absence): Flow<NetworkResult<Absence>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = absenceApi.createAbsence(AbsenceDto.fromDomain(absence))
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!.toDomain()))
            } else {
                emit(NetworkResult.Error("Erreur lors de la création de l'absence"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Erreur inconnue"))
        }
    }

    fun updateAbsence(absence: Absence): Flow<NetworkResult<Absence>> = flow {
        emit(NetworkResult.Loading())
        val id = absence.id ?: return@flow emit(NetworkResult.Error("ID d'absence manquant pour la mise à jour"))
        try {
            val response = absenceApi.updateAbsence(id, AbsenceDto.fromDomain(absence))
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!.toDomain()))
            } else {
                emit(NetworkResult.Error("Erreur lors de la mise à jour de l'absence"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Erreur inconnue"))
        }
    }

    fun validateAbsence(absenceId: Long): Flow<NetworkResult<Absence>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = absenceApi.validateAbsence(absenceId)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!.toDomain()))
            } else {
                emit(NetworkResult.Error("Erreur lors de la validation de l'absence"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Erreur inconnue"))
        }
    }

    fun deleteAbsence(id: Long): Flow<NetworkResult<Unit>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = absenceApi.deleteAbsence(id)
            if (response.isSuccessful) {
                emit(NetworkResult.Success(Unit))
            } else {
                emit(NetworkResult.Error("Erreur lors de la suppression de l'absence"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Erreur inconnue"))
        }
    }
}