package com.ensa.gestionpersonnel.data.repository

import com.ensa.gestionpersonnel.data.remote.api.DiplomeApi
import com.ensa.gestionpersonnel.data.remote.dto.DiplomeDto
import com.ensa.gestionpersonnel.domain.model.Diplome
import com.ensa.gestionpersonnel.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DiplomeRepository @Inject constructor(
    private val diplomeApi: DiplomeApi
) {

    fun getAllDiplomes(): Flow<NetworkResult<List<Diplome>>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = diplomeApi.getAllDiplomes()
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!.map { it.toDomain() }))
            } else {
                emit(NetworkResult.Error("Impossible de charger les diplômes : ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Erreur réseau"))
        }
    }

    fun getDiplomesByPersonnel(personnelId: Long): Flow<NetworkResult<List<Diplome>>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = diplomeApi.getDiplomesByPersonnel(personnelId)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!.map { it.toDomain() }))
            } else {
                emit(NetworkResult.Error("Impossible de charger les diplômes"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Erreur réseau"))
        }
    }
    
    fun getDiplomeById(id: Long): Flow<NetworkResult<Diplome>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = diplomeApi.getDiplomeById(id)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!.toDomain()))
            } else {
                emit(NetworkResult.Error("Diplôme introuvable"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Erreur réseau"))
        }
    }
    
    fun createDiplome(diplome: Diplome): Flow<NetworkResult<Diplome>> = flow {
        emit(NetworkResult.Loading())
        try {
            val dto = DiplomeDto.fromDomain(diplome)
            val response = diplomeApi.createDiplome(dto)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!.toDomain()))
            } else {
                emit(NetworkResult.Error("Échec de la création : ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Erreur réseau"))
        }
    }
    
    fun updateDiplome(diplome: Diplome): Flow<NetworkResult<Diplome>> = flow {
        emit(NetworkResult.Loading())
        try {
            val dto = DiplomeDto.fromDomain(diplome)
            val response = diplomeApi.updateDiplome(diplome.id, dto)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!.toDomain()))
            } else {
                emit(NetworkResult.Error("Échec de la mise à jour"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Erreur réseau"))
        }
    }
    
    fun deleteDiplome(id: Long): Flow<NetworkResult<Unit>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = diplomeApi.deleteDiplome(id)
            if (response.isSuccessful) {
                emit(NetworkResult.Success(Unit))
            } else {
                emit(NetworkResult.Error("Échec de la suppression"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Erreur réseau"))
        }
    }
}
