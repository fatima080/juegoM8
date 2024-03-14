package com.example.juegoM8

import android.graphics.Bitmap

data class Jugador(
    val Nom: String,
    val Puntuacio: String,
    val ImatgeUrl: String,
    var imatgeBitmap: Bitmap? = null // Nueva propiedad para almacenar la imagen como un Bitmap
)
