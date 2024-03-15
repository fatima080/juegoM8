package com.example.juegoM8.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.juegoM8.Jugador
import com.example.juegoM8.R
import com.squareup.picasso.Picasso

class JugadorsViewHolder (itemView: View):RecyclerView.ViewHolder(itemView) {
    //afegim les variables que apunten als continguts del layout
    val Nom : TextView = itemView.findViewById(R.id.tvNom_Jugador)
    val Puntuacio : TextView = itemView.findViewById(R.id.tvPuntuacio_Jugador)
    val Imatge : ImageView = itemView.findViewById(R.id.ivJugador)

    fun bind(jugador: Jugador) {
        Nom.text = jugador.Nom
        Puntuacio.text = jugador.Puntuacio
        // Aquí deberías cargar la imagen utilizando Picasso o cualquier otra biblioteca de carga de imágenes
        Picasso.get().load(jugador.Imatge).resize(150,150).into(Imatge)

        Imatge.setOnClickListener(){
            Toast.makeText(Imatge.context, jugador.Nom, Toast.LENGTH_LONG).show()
        }

        //itemView.setOnClickListener(){onClickListener(jugador)}

    }

    /*fun render(jugadorModel: Jugador, onClickListener:(Jugador) -> Unit){
        //la cridara per a cada jugador
        nomJugador.text=jugadorModel.Nom
        puntuacioJugador.text=jugadorModel.Puntuacio //És un string
        Picasso.get().load(jugadorModel.ImatgeUrl).resize(150,150).into(Imatge)

        /*foto.setOnClickListener(){
            Toast.makeText(foto.context, jugadorModel.nom_jugador,Toast.LENGTH_LONG).show()
        }*/

        itemView.setOnClickListener(){onClickListener(jugadorModel)}
    }*/
}