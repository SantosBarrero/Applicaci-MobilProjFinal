package com.example.applicacimobilprojfinal.api

import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class DataApiLocations : CoroutineScope {
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val baseUrl = "https://us1.locationiq.com/"  // Assegura't que el subdomini (us1) és correcte pel teu compte

    private fun getClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    private fun getRetrofit(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun obtenirCoordenades(apiKey: String, adreça: String): List<CoordenadesResposta>? {
        var respostaCoordenades: List<CoordenadesResposta>? = null
        runBlocking {
            var resposta: Response<List<CoordenadesResposta>>? = null
            val cor = launch {
                resposta = getRetrofit().create(ApiService::class.java)
                    .getCoordenades(apiKey, adreça)
            }
            cor.join()
            if (resposta!!.isSuccessful) {
                respostaCoordenades = resposta!!.body()
            } else {
                Log.e("GET COORD", "Error: ${resposta!!.errorBody()?.string()}")
                respostaCoordenades = null
            }
        }
        return respostaCoordenades
    }
}
