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

    private val url = "https://smalltealsled74.conveyor.cloud/api/"

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

    fun getComerçId(id: Int?): Comerc? {
        var comerç: Comerc? = null
        runBlocking {
            var resposta: Response<Comerc>? = null
            val cor = launch {
                resposta = getRetrofit().create(ApiService::class.java).getComerçPerId(id!!)
            }
            cor.join()
            if (resposta!!.isSuccessful)
                comerç = resposta!!.body()
            else
                comerç = null
        }
        return comerç
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

    suspend fun insertarEncarec(encarrec: Encarrec): Encarrec? {
        return try {
            val call = getRetrofit().create(ApiService::class.java).postEncarrec(encarrec)
            if (call.isSuccessful) {
                call.body()
            } else {
                Log.e("POST", "Error en la resposta: ${call.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("POST", "Error al fer el POST", e)
            null
        }
    }


    fun getEncarrecsId(id: Int?): List<Encarrec>? {
        var encarrec: List<Encarrec>? = null
        runBlocking {
            var resposta: Response<List<Encarrec>>? = null
            val cor = launch {
                resposta = getRetrofit().create(ApiService::class.java).getEncarrecsPerId(id!!)
            }
            cor.join()
            if (resposta!!.isSuccessful)
                encarrec = resposta!!.body()
            else
                encarrec = null
        }
        return encarrec
    }

    fun getStck(id: Int): List<Producte>? {
        var productes: List<Producte>? = null
        runBlocking {
            var resposta: Response<List<Producte>>? = null
            val cor = launch {
                resposta = getRetrofit().create(ApiService::class.java).getStockSucur(id)
            }
            cor.join()
            if (resposta!!.isSuccessful)
                productes = resposta!!.body()
            else
                productes = null
        }
        return productes
    }

    fun getProducte(codi: String): Producte? {
        var producte: Producte? = null
        runBlocking {
            var resposta: Response<Producte>? = null
            val cor = launch {
                resposta = getRetrofit().create(ApiService::class.java).getProducte(codi)
            }
            cor.join()
            if (resposta!!.isSuccessful)
                producte = resposta!!.body()
            else
                producte = null
        }
        return producte
    }

    suspend fun insertarProdEncarr(pe: ProducteEncarrec): Boolean {
        return try {
            val call = getRetrofit().create(ApiService::class.java).postProdEncarrec(pe)
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
}


