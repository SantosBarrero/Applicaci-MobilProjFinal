package com.example.applicacimobilprojfinal.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("Usuaris/")
    suspend fun getUsuari(): Response<List<Usuari>>

    @POST("Usuaris/")
    suspend fun postUsuari(@Body usuari: Usuari): Response<Usuari>
}