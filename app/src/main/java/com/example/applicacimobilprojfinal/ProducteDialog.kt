package com.example.applicacimobilprojfinal

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.applicacimobilprojfinal.api.DataApi
import com.example.applicacimobilprojfinal.api.Encarrec
import com.example.applicacimobilprojfinal.api.Producte
import com.example.applicacimobilprojfinal.api.ProducteEncarrec
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProducteDialog(context: Context, private val producte: Producte, private val sucursalId: Int, private val usuId : Int, private val onProducteAfegit: (ProducteEncarrec) -> Unit) : Dialog(context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialeg_producte)

        val dataApi = DataApi()
        val nom: TextView = findViewById(R.id.dialogNomProducte)
        val imatge: ImageView = findViewById(R.id.dialogImatge)
        val descripcio: TextView = findViewById(R.id.dialogDescripcio)
        val quantitat: EditText = findViewById(R.id.txtQuantitat)
        val sumar: ImageView = findViewById(R.id.btnSumar)
        val restar: ImageView = findViewById(R.id.btnRestar)
        val preu: TextView = findViewById(R.id.dialogPreu)
        val btnAfegir = findViewById<Button>(R.id.btnAfegirEncàrrec)

        nom.text = producte.nom
        descripcio.text = producte.descripcio ?: "Sense descripció"
        preu.text = "%.2f€".format(producte.preu ?: 0.0)

        Log.i("usu", usuId.toString())

        sumar.setOnClickListener {
            val actual = quantitat.text.toString().toIntOrNull() ?: 1
            quantitat.setText((actual + 1).toString())
        }

        restar.setOnClickListener {
            val actual = quantitat.text.toString().toIntOrNull() ?: 1
            if (actual > 1) {
                quantitat.setText((actual - 1).toString())
            }
        }

        btnAfegir.setOnClickListener {
            val q = quantitat.text.toString().toIntOrNull() ?: 1

            val producteEncarrec = ProducteEncarrec(
                codiDeBarres = producte.codiDeBarres,
                encarrecId = 0,
                quantitat = q
            )

            onProducteAfegit(producteEncarrec)
            dismiss()
        }
    }
}