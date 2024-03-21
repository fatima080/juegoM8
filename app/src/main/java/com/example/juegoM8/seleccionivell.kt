package com.example.juegoM8

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.example.juegoM8.Nivel1 as Nivel1

private var NOM: String =""
private var PUNTUACIO: String=""
private var UID: String=""
private var NIVELL: String=""
lateinit var imageButton1 :ImageButton
lateinit var imageButton2 :ImageButton
lateinit var imageButton3 :ImageButton
class seleccionivell : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccionivell)

        //busco els 3 butons
        imageButton1 = findViewById(R.id.imageButton)
        imageButton2 = findViewById(R.id.imageButton2)
        imageButton3 = findViewById(R.id.imageButton3)
        actualizarVisibilidadBotones()
        imageButton1.setOnClickListener {
            val intent = Intent(this, Nivel1::class.java)
            intent.putExtra("UID", UID)
            intent.putExtra("NOM", NOM)
            intent.putExtra("PUNTUACIO", PUNTUACIO)
            intent.putExtra("NIVELL", NIVELL)
            startActivity(intent)
            finish()
        }

        imageButton2.setOnClickListener {
            val intent = Intent(this, Nivel2::class.java)
            intent.putExtra("UID", UID)
            intent.putExtra("NOM", NOM)
            intent.putExtra("PUNTUACIO", PUNTUACIO)
            intent.putExtra("NIVELL", NIVELL)
            startActivity(intent)
            finish()
        }

        imageButton3.setOnClickListener {
            val intent = Intent(this, Nivel3::class.java)
            intent.putExtra("UID", UID)
            intent.putExtra("NOM", NOM)
            intent.putExtra("PUNTUACIO", PUNTUACIO)
            intent.putExtra("NIVELL", NIVELL)
            startActivity(intent)
            finish()
        }



    }
    override fun onResume() {
        super.onResume()
        // Recuperar los valores de los parámetros nuevamente
        var intent: Bundle? = intent.extras
        UID = intent?.get("UID").toString()
        NOM = intent?.get("NOM").toString()
        PUNTUACIO = intent?.get("PUNTUACIO").toString()
        NIVELL = intent?.get("NIVELL").toString()

        // Actualizar la visibilidad de los botones según el nivel
        actualizarVisibilidadBotones()
    }
    private fun actualizarVisibilidadBotones() {
        // Deshabilitar y ocultar todos los botones inicialmente
        imageButton1.isEnabled = false
        imageButton1.visibility = View.GONE
        imageButton2.isEnabled = false
        imageButton2.visibility = View.GONE
        imageButton3.isEnabled = false
        imageButton3.visibility = View.GONE

        // Mostrar los botones según el nivel
        when (NIVELL) {
            "1" -> {
                Toast.makeText(this, "NIVELL 1", Toast.LENGTH_LONG).show()
                imageButton1.isEnabled = true
                imageButton1.visibility = View.VISIBLE
            }
            "2" -> {
                Toast.makeText(this, "NIVELL 2", Toast.LENGTH_LONG).show()
                imageButton2.isEnabled = true
                imageButton2.visibility = View.VISIBLE
            }
            "3" -> {
                Toast.makeText(this, "NIVELL 3", Toast.LENGTH_LONG).show()
                imageButton3.isEnabled = true
                imageButton3.visibility = View.VISIBLE
            }
            "4" -> {
                imageButton1.isEnabled = true
                imageButton1.visibility = View.VISIBLE
                imageButton2.isEnabled = true
                imageButton2.visibility = View.VISIBLE
                imageButton3.isEnabled = true
                imageButton3.visibility = View.VISIBLE
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, Menu::class.java)
        intent.putExtra("UID", UID)
        intent.putExtra("NOM", NOM)
        intent.putExtra("PUNTUACIO", PUNTUACIO)
        intent.putExtra("NIVELL", NIVELL)
        startActivity(intent)
        finish()
    }



}