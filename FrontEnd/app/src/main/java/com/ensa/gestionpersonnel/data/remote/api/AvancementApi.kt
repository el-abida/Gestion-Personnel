package com.ensa.gestionpersonnel.data.remote.api

import com.ensa.gestionpersonnel.data.remote.dto.AvancementDto
import retrofit2.Response
import retrofit2.http.*

interface AvancementApi {

    @GET("avancements")
    suspend fun getAllAvancements(): Response<List<AvancementDto>>

    @GET("avancements/{id}")
    suspend fun getAvancementById(@Path("id") id: Long): Response<AvancementDto>

    @GET("avancements/personnel/{id}")
    suspend fun getAvancementsByPersonnelId(@Path("id") personnelId: Long): Response<List<AvancementDto>>

    @POST("avancements")
    suspend fun createAvancement(@Body avancementDto: AvancementDto): Response<AvancementDto>

    @PUT("avancements/{id}")
    suspend fun updateAvancement(@Path("id") id: Long, @Body avancementDto: AvancementDto): Response<AvancementDto>

    @DELETE("avancements/{id}")
    suspend fun deleteAvancement(@Path("id") id: Long): Response<Unit>
}
