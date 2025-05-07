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

    @GET("Sucursals/")
    suspend fun getSucursal(): Response<List<Sucursal>>

    @GET("v1/search")
    suspend fun getCoordenades(
        @Query("key") apiKey: String,
        @Query("q") adreça: String,
        @Query("format") format: String = "json"
    ): Response<List<CoordenadesResposta>>

}