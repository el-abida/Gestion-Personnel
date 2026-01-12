package com.ensa.gestionpersonnel.data.remote.api

import com.ensa.gestionpersonnel.data.remote.dto.AbsenceDto
import retrofit2.Response
import retrofit2.http.*

interface AbsenceApi {

    @GET("absences")
    suspend fun getAllAbsences(): Response<List<AbsenceDto>>

    @GET("absences/{id}")
    suspend fun getAbsenceById(@Path("id") id: Long): Response<AbsenceDto>

    @GET("absences/personnel/{personnelId}")
    suspend fun getAbsencesByPersonnelId(@Path("personnelId") personnelId: Long): Response<List<AbsenceDto>>

    @POST("absences")
    suspend fun createAbsence(@Body absenceDto: AbsenceDto): Response<AbsenceDto>

    @PUT("absences/{id}")
    suspend fun updateAbsence(@Path("id") id: Long, @Body absenceDto: AbsenceDto): Response<AbsenceDto>

    @PUT("absences/{id}/validate")
    suspend fun validateAbsence(@Path("id") id: Long): Response<AbsenceDto>

    @DELETE("absences/{id}")
    suspend fun deleteAbsence(@Path("id") id: Long): Response<Unit>
}