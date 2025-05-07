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

class DataApi : CoroutineScope {
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val url = "https://smallaquapencil56.conveyor.cloud/api/"

    private fun getClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    private fun getRetrofit(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(url)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getLlistaUsuaris(): List<Usuari>? {
        var usuaris: List<Usuari>? = null
        runBlocking {
            var resposta: Response<List<Usuari>>? = null
            val cor = launch {
                resposta = getRetrofit().create(ApiService::class.java).getUsuari()
            }
            cor.join()
            if (resposta!!.isSuccessful)
                usuaris = resposta!!.body()
            else
                usuaris = null
        }
        return usuaris
    }

    suspend fun insertarUsuari(usuari: Usuari): Boolean {
        return try {
            val call = getRetrofit().create(ApiService::class.java).postUsuari(usuari)
            if (call.isSuccessful) {
                Log.i("POST", "Usuari creat: ${call.body()}")
            } else {
                Log.e("POST", "Error en la resposta: ${call.errorBody()?.string()}")
            }
            call.isSuccessful
        } catch (e: Exception) {
            Log.e("POST", "Error al fer el POST", e)
            false
        }
    }

    fun getLlistaSucursals(): List<Sucursal>? {
        var sucursals: List<Sucursal>? = null
        runBlocking {
            var resposta: Response<List<Sucursal>>? = null
            val cor = launch {
                resposta = getRetrofit().create(ApiService::class.java).getSucursal()
            }
            cor.join()
            if (resposta!!.isSuccessful)
                sucursals = resposta!!.body()
            else
                sucursals = null
        }
        return sucursals
    }
}
