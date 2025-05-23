package com.example.applicacimobilprojfinal


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.applicacimobilprojfinal.R
import com.example.applicacimobilprojfinal.adapters.ProducteAdapter
import com.example.applicacimobilprojfinal.api.DataApi
import com.example.applicacimobilprojfinal.api.Encarrec
import com.example.applicacimobilprojfinal.api.Producte
import com.example.applicacimobilprojfinal.api.ProducteEncarrec
import com.example.applicacimobilprojfinal.databinding.ActivityPantallaProductosBinding
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PantallaProductos : AppCompatActivity() {
    private val dataApi = DataApi()
    private lateinit var binding: ActivityPantallaProductosBinding
    private val llistaProductesEncarrec = mutableListOf<ProducteEncarrec>()
    private lateinit var encarrec: Encarrec
    private var productes: List<Producte>? = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantallaProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sucursalId = intent.getIntExtra("sucursalId", -1)
        val usuId = intent.getIntExtra("usuari", -1)


        CoroutineScope(Dispatchers.Main).launch {
            productes = withContext(Dispatchers.IO) {
                val stockList = dataApi.getStck(sucursalId)
                stockList?.mapNotNull { stubProducte ->
                    dataApi.getProducte(stubProducte.codiDeBarres.toString())
                }
            }

            if (productes != null) {
                Log.i("ProductesComplets", productes.toString())
                val adapter = ProducteAdapter(productes, sucursalId, usuId) { producteEncarrec -> llistaProductesEncarrec.add(producteEncarrec)
                    Log.d("Afegit", "Afegit: $producteEncarrec") }
                binding.rcv1.layoutManager = GridLayoutManager(this@PantallaProductos, 2)
                binding.rcv1.adapter = adapter

                val snapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(binding.rcv1)
            } else {
                Log.e("ERROR", "No s'han pogut obtenir els productes")
            }
        }

        val butENcarr = findViewById<Button>(R.id.btnAfegirEncàrrecFIn)

        butENcarr.setOnClickListener{
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            encarrec = Encarrec(
                encarrecId = 0,
                preuTotal = llistaProductesEncarrec.sumOf { it.quantitat * (productes!!.find { p -> p.codiDeBarres == it.codiDeBarres }?.preu ?: 0.0) },
                data = currentDate,
                pagat = false,
                estat = "No Preparat",
                sucursalId = sucursalId,
                usuId = usuId,
                productes = emptyList()
            )

            val intent = Intent(this, PantallaCompra::class.java).apply {
                putExtra("encarrec", encarrec)
                putExtra("productesEncarrec", ArrayList(llistaProductesEncarrec))
            }
            startActivity(intent)

        }
    }
}
