package com.example.applicacimobilprojfinal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.applicacimobilprojfinal.ProducteDialog
import com.example.applicacimobilprojfinal.R
import com.example.applicacimobilprojfinal.api.Producte
import com.example.applicacimobilprojfinal.api.ProducteEncarrec

class ProducteAdapter(private val productes: List<Producte>?, private val sucursalId: Int, private val usuId: Int, private val onProducteAfegit: (ProducteEncarrec) -> Unit) : RecyclerView.Adapter<ProducteAdapter.ProducteViewHolder>() {


    class ProducteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nom: TextView = itemView.findViewById(R.id.nomProducte)
        val preu: TextView = itemView.findViewById(R.id.preuProducte)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProducteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.producte_lay, parent, false)
        return ProducteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProducteViewHolder, position: Int) {
        val producte = productes!![position]

        holder.nom.text = producte.nom ?: "Sense nom"
        holder.preu.text = producte.preu?.let { "Preu: %.2f€".format(it) } ?: "Preu desconegut"


        holder.itemView.setOnClickListener{

            val context = holder.itemView.context
            val dialog = ProducteDialog(context, producte, sucursalId, usuId) { prodEnc ->
                onProducteAfegit(prodEnc)
            }
            dialog.show()
        }

    }

    override fun getItemCount(): Int = productes!!.size
}
