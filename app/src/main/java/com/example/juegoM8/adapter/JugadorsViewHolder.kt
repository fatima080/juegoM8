package com.example.juegoM8.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.juegoM8.Jugador
import com.example.juegoM8.R
import com.squareup.picasso.Picasso

class JugadorsViewHolder (view: View):RecyclerView.ViewHolder(view) {
    //afegim les variables que apunten als continguts del layout
    val nomJugador=view.findViewById<TextView>(R.id.tvNom_Jugador)
    val puntuacioJugador=view.findViewById<TextView>(R.id.tvPuntuacio_Jugador)
    val Imatge=view.findViewById<ImageView>(R.id.ivJugador)

    fun render(jugadorModel: Jugador, onClickListener:(Jugador) -> Unit){
        //la cridara per a cada jugador
        nomJugador.text=jugadorModel.Nom
        puntuacioJugador.text=jugadorModel.Puntuacio //És un string
        Picasso.get().load(jugadorModel.ImatgeUrl).resize(150,150).into(Imatge)

        /*foto.setOnClickListener(){
            Toast.makeText(foto.context, jugadorModel.nom_jugador,Toast.LENGTH_LONG).show()
        }*/

        itemView.setOnClickListener(){onClickListener(jugadorModel)}
    }
}