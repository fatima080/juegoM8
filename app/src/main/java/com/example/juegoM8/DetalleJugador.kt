package com.example.juegoM8

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class DetalleJugador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_jugador)

        val ImatgeDetall: ImageView = findViewById(R.id.imatgeD)
        // Obtén la referencia a los TextViews y Labels
        val NomDetall: TextView = findViewById(R.id.nomD)
        val PuntuacioDetall: TextView = findViewById(R.id.puntuacioD)
        val DataDetall: TextView = findViewById(R.id.dataD)
        val EdatDetall: TextView = findViewById(R.id.edatD)
        val EmailDetall: TextView = findViewById(R.id.emailD)
        val PoblacioDetall: TextView = findViewById(R.id.poblacioD)

        val PuntuacioLabel: TextView = findViewById(R.id.puntuacioLabel)
        val DataLabel: TextView = findViewById(R.id.dataLabel)
        val EdatLabel: TextView = findViewById(R.id.edatLabel)
        val EmailLabel: TextView = findViewById(R.id.emailLabel)
        val PoblacioLabel: TextView = findViewById(R.id.poblacioLabel)

        // Carga el tipo de letra personalizado desde los assets
        val typeface = Typeface.createFromAsset(assets, "fonts/Pulang.ttf")

        // Asigna el tipo de letra personalizado a los TextViews
        NomDetall.typeface = typeface
        PuntuacioDetall.typeface = typeface
        DataDetall.typeface = typeface
        EdatDetall.typeface = typeface
        EmailDetall.typeface = typeface
        PoblacioDetall.typeface = typeface

        // Asigna el tipo de letra personalizado a los Labels
        PuntuacioLabel.typeface = typeface
        DataLabel.typeface = typeface
        EdatLabel.typeface = typeface
        EmailLabel.typeface = typeface
        PoblacioLabel.typeface = typeface


        val bundle: Bundle? = intent.extras
        val Nom = bundle?.getString("Nom")
        val Puntuacio = bundle?.getString("Puntuacio")
        val Imatge = bundle?.getString("Imatge")
        val Data = bundle?.getString("Data")
        val Edat = bundle?.getString("Edat")
        val Email = bundle?.getString("Email")
        val Poblacio = bundle?.getString("Poblacio")

        NomDetall.text = Nom
        PuntuacioDetall.text = Puntuacio
        // Asigna el ImagenView adecuadamente con la librería Picasso o Glide según corresponda
        Picasso.get().load(Imatge).into(ImatgeDetall)
        DataDetall.text = Data
        EdatDetall.text = Edat
        EmailDetall.text = Email
        PoblacioDetall.text = Poblacio



    }
}