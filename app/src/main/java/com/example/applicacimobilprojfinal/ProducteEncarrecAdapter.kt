package com.example.applicacimobilprojfinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicacimobilprojfinal.api.ProducteEncarrec

class ProducteEncarrecAdapter(
    private val llista: List<ProducteEncarrec>,
    val nomsProductes: List<String>
) : RecyclerView.Adapter<ProducteEncarrecAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomText: TextView = view.findViewById(R.id.textNom)
        val quantitatText: TextView = view.findViewById(R.id.textQuantitat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producte, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = llista[position]
        val noms = nomsProductes[position]
        holder.nomText.text = noms.toString()
        holder.quantitatText.text = item.quantitat.toString()
    }

    override fun getItemCount(): Int = llista.size
}
