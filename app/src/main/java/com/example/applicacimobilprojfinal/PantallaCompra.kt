package com.example.applicacimobilprojfinal

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicacimobilprojfinal.api.DataApi
import com.example.applicacimobilprojfinal.api.Encarrec
import com.example.applicacimobilprojfinal.api.ProducteEncarrec
import com.example.applicacimobilprojfinal.databinding.ActivityPantallaCompraBinding
import kotlinx.coroutines.*


class PantallaCompra : AppCompatActivity() {
    lateinit var binding : ActivityPantallaCompraBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPantallaCompraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dataApi = DataApi()

        val encarrec = intent.getParcelableExtra<Encarrec>("encarrec")
        val productesEncarrec =
            intent.getParcelableArrayListExtra<ProducteEncarrec>("productesEncarrec")
        val butPost = findViewById<Button>(R.id.postEncarrec)
        val recycler = findViewById<RecyclerView>(R.id.recyclerViewProductes)
        recycler.layoutManager = LinearLayoutManager(this)


        binding.totalTextView.text = "Total: %.2f€".format(encarrec!!.preuTotal)

        CoroutineScope(Dispatchers.IO).launch {
            val nomsProductes = obtenirNomsProductes(productesEncarrec ?: emptyList(), dataApi)
            withContext(Dispatchers.Main) {
                recycler.adapter = ProducteEncarrecAdapter(productesEncarrec ?: emptyList(), nomsProductes)
            }
        }


        butPost.setOnClickListener {
            if (encarrec != null && productesEncarrec != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val resposta = dataApi.insertarEncarec(encarrec)

                        if (resposta != null) {
                            val idDelEncarrecRetornat = resposta.encarrecId

                            for (pe in productesEncarrec) {
                                val producteEncarrec = pe.copy(encarrecId = idDelEncarrecRetornat)
                                dataApi.insertarProdEncarr(producteEncarrec)
                            }

                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@PantallaCompra,
                                    "Encàrrec afegit correctament",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@PantallaCompra,
                                    "No s'ha pogut obtenir l'ID de l'encàrrec",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@PantallaCompra,
                                "Error en afegir l'encàrrec o el producte",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e("PantallaCompra", "Error POST", e)
                        }
                    }
                }
            }
        }
    }

    private fun obtenirNomsProductes(
        productesEncarrec: List<ProducteEncarrec>,
        dataApi: DataApi
    ): List<String> {
        val noms = mutableListOf<String>()
        for (pe in productesEncarrec) {
            val producte = dataApi.getProducte(pe.codiDeBarres ?: "")
            if (producte != null) {
                noms.add(producte.nom ?: "Nom desconegut")
            } else {
                noms.add("Nom desconegut")
            }
        }
        return noms
    }

}


