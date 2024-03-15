package com.example.juegoM8.adapter

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.juegoM8.Jugador
import com.example.juegoM8.R

class JugadorsAdapter(private val jugadorList : ArrayList<Jugador>) : RecyclerView.Adapter<JugadorsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JugadorsViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context).inflate(R.layout.item_jugador, parent, false)
        return JugadorsViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return jugadorList.size
    }

    override fun onBindViewHolder(holder: JugadorsViewHolder, position: Int) {
        //aquest mètode és que va passant per cada un dels items i crida al render
        val jugador = jugadorList[position]
        holder.bind(jugador)
    }
}