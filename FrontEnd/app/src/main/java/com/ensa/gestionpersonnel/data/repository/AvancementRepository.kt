package com.ensa.gestionpersonnel.data.repository

import com.ensa.gestionpersonnel.data.remote.api.AvancementApi
import com.ensa.gestionpersonnel.data.remote.dto.AvancementDto
import com.ensa.gestionpersonnel.domain.model.Avancement
import com.ensa.gestionpersonnel.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AvancementRepository @Inject constructor(
    private val avancementApi: AvancementApi
) {

    fun getAllAvancements(): Flow<NetworkResult<List<Avancement>>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = avancementApi.getAllAvancements()
            if (response.isSuccessful && response.body() != null) {
                val avancements = response.body()!!.map { it.toDomain() }
                emit(NetworkResult.Success(avancements))
            } else {
                emit(NetworkResult.Error("Failed to fetch avancements: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }

    fun getAvancementById(id: Long): Flow<NetworkResult<Avancement>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = avancementApi.getAvancementById(id)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!.toDomain()))
            } else {
                emit(NetworkResult.Error("Avancement not found"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }

    fun getAvancementsByPersonnelId(personnelId: Long): Flow<NetworkResult<List<Avancement>>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = avancementApi.getAvancementsByPersonnelId(personnelId)
            if (response.isSuccessful && response.body() != null) {
                val avancements = response.body()!!.map { it.toDomain() }
                emit(NetworkResult.Success(avancements))
            } else {
                emit(NetworkResult.Error("Failed to fetch avancements for personnel"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }

    fun createAvancement(avancement: Avancement): Flow<NetworkResult<Avancement>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = avancementApi.createAvancement(AvancementDto.fromDomain(avancement))
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!.toDomain()))
            } else {
                emit(NetworkResult.Error("Failed to create avancement"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }

    fun updateAvancement(avancement: Avancement): Flow<NetworkResult<Avancement>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = avancementApi.updateAvancement(avancement.id, AvancementDto.fromDomain(avancement))
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!.toDomain()))
            } else {
                emit(NetworkResult.Error("Failed to update avancement"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }

    fun deleteAvancement(id: Long): Flow<NetworkResult<Unit>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = avancementApi.deleteAvancement(id)
            if (response.isSuccessful) {
                emit(NetworkResult.Success(Unit))
            } else {
                emit(NetworkResult.Error("Failed to delete avancement"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }
}
