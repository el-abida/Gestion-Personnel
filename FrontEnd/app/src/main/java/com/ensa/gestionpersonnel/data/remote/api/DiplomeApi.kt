package com.ensa.gestionpersonnel.data.remote.api

import com.ensa.gestionpersonnel.data.remote.dto.DiplomeDto
import retrofit2.Response
import retrofit2.http.*

interface DiplomeApi {
    
    @GET("diplomes")
    suspend fun getAllDiplomes(): Response<List<DiplomeDto>>
    
    @GET("diplomes/personnel/{id}")
    suspend fun getDiplomesByPersonnel(@Path("id") id: Long): Response<List<DiplomeDto>>
    
    @GET("diplomes/{id}")
    suspend fun getDiplomeById(@Path("id") id: Long): Response<DiplomeDto>
    
    @POST("diplomes")
    suspend fun createDiplome(@Body diplome: DiplomeDto): Response<DiplomeDto>
    
    @PUT("diplomes/{id}")
    suspend fun updateDiplome(@Path("id") id: Long, @Body diplome: DiplomeDto): Response<DiplomeDto>
    
    @DELETE("diplomes/{id}")
    suspend fun deleteDiplome(@Path("id") id: Long): Response<Unit>
}
