package com.example.applicacimobilprojfinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicacimobilprojfinal.adapters.ProducteAdapter
import com.example.applicacimobilprojfinal.api.Encarrec
import com.example.applicacimobilprojfinal.api.Producte

class EncarrecsAdaptador(private val encarrecs : List<Encarrec>?) : RecyclerView.Adapter<EncarrecsAdaptador.EncarrecsAdaptadorViewHolder>() {

    class EncarrecsAdaptadorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numEncarr : TextView = itemView.findViewById(R.id.TextVNumEnc)
        val dia : TextView = itemView.findViewById(R.id.Dia)
        val estat : TextView = itemView.findViewById(R.id.Estat)
        val preuTot : TextView = itemView.findViewById(R.id.Preu)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EncarrecsAdaptador.EncarrecsAdaptadorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_encarrecs, parent, false)
        return EncarrecsAdaptadorViewHolder(view)
    }

    override fun onBindViewHolder(holder: EncarrecsAdaptador.EncarrecsAdaptadorViewHolder, position: Int) {
        val encarrec = encarrecs!![position]

        holder.numEncarr.text = "Encarrec ${encarrec.encarrecId.toString()}"
        holder.dia.text = "${encarrec.data.toString()}"
        holder.estat.text = "${encarrec.estat.toString()}"
        holder.preuTot.text = "${encarrec.preuTotal.toString()} € "

    }

    override fun getItemCount(): Int = encarrecs!!.size
}