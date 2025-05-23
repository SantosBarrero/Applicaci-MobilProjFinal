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

    @GET("Comerç/{id}")
    suspend fun getComerçPerId(@retrofit2.http.Path("id") id: Int): Response<Comerc>

    @GET("Stock/Sucur/{id}")
    suspend fun getStockSucur(@retrofit2.http.Path("id") id: Int): Response<List<Producte>>

    @GET("Encarrecs/Usuari/{id}")
    suspend fun getEncarrecsPerId(@retrofit2.http.Path("id") id: Int): Response<List<Encarrec>>

    @POST("Encarrecs/")
    suspend fun postEncarrec(@Body encarrec: Encarrec): Response<Encarrec>

    @GET("Productes/{codi}")
    suspend fun getProducte(@retrofit2.http.Path("codi") codi : String): Response<Producte>

    @POST("ProducteEncarrec/")
    suspend fun postProdEncarrec(@Body Pe : ProducteEncarrec) : Response<ProducteEncarrec>

    @GET("v1/search")
    suspend fun getCoordenades(
        @Query("key") apiKey: String,
        @Query("q") adreça: String,
        @Query("format") format: String = "json"
    ): Response<List<CoordenadesResposta>>

}