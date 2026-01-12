package com.ensa.gestionpersonnel.data.remote.api

import com.ensa.gestionpersonnel.domain.model.ResponsableRH
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileApi {

    @GET("profile/{id}")
    suspend fun getProfile(@Path("id") id: Long): Response<ResponsableRH>

    @PUT("profile/{id}")
    suspend fun updateProfile(
        @Path("id") id: Long,
        @Body profile: ResponsableRH
    ): Response<ResponsableRH>
}
