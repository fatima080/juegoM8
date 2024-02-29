package com.example.juegoM8

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.ImageButton
import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

private var NOM: String =""
private var PUNTUACIO: String=""
private var UID: String=""
private var NIVELL: String=""

class Nivel1 : AppCompatActivity() {

    private val COLUMNAS = 3
    private var botonpulsado1 : Int=0;
    private var botonpulsado2 : Int=0;
    private var movimientos: Int=0;
    lateinit var continuarBtn: Button
    val tf = Typeface.createFromAsset(assets,"fonts/Pulang.ttf")
    private lateinit var graella: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nivel1)
        var intent:Bundle? = intent.extras
        UID = intent?.get("UID").toString()
        NOM = intent?.get("NOM").toString()
        PUNTUACIO = intent?.get("PUNTUACIO").toString()
        NIVELL = intent?.get("NIVELL").toString()
        continuarBtn = findViewById(R.id.continuarBtn)
        continuarBtn.setTypeface(tf)
        continuarBtn.visibility = View.INVISIBLE
        inicializar()
        mezclar()
        mostrar()
    }
    private fun mostrar() {
        val buttons = listOf(
            findViewById<ImageButton>(R.id.button),
            findViewById<ImageButton>(R.id.button2),
            findViewById<ImageButton>(R.id.button3),
            findViewById<ImageButton>(R.id.button4),
            findViewById<ImageButton>(R.id.button5),
            findViewById<ImageButton>(R.id.button6),
            findViewById<ImageButton>(R.id.button7),
            findViewById<ImageButton>(R.id.button8),
            findViewById<ImageButton>(R.id.button9)
        )

        for (i in graella.indices) {
            val button = buttons[i]

            // Configuración del fondo del botón según el valor en graella[i]
            when (graella[i]) {
                "0" -> button.setBackgroundResource(R.drawable.gatouno)
                "1" -> button.setBackgroundResource(R.drawable.gatodos)
                "2" -> button.setBackgroundResource(R.drawable.gatotres)
                "3" -> button.setBackgroundResource(R.drawable.gatocuatro)
                "4" -> button.setBackgroundResource(R.drawable.gatocinco)
                "5" -> button.setBackgroundResource(R.drawable.gatoseis)
                "6" -> button.setBackgroundResource(R.drawable.gatosiete)
                "7" -> button.setBackgroundResource(R.drawable.gatoocho)
                "8" -> button.setBackgroundResource(R.drawable.gatonueve)
            }
            button.tag = i
            button.setOnClickListener {
                vibrar(this, 100)
                val id = it.id
                if (botonpulsado1 == 0) {
                    // Primer botón pulsado
                    Log.i("MYTAG", id.toString());
                    botonpulsado1 = id;
                } else if (botonpulsado2 == 0) {
                    // Segundo botón pulsado
                    botonpulsado2 = id;
                    // Realizar el intercambio de imágenes de fondo si los botones son adyacentes
                    intercambiarImagenesSiAdyacentes(botonpulsado1, botonpulsado2)
                    // Reiniciar las variables para el próximo par de botones
                    botonpulsado1 = 0
                    botonpulsado2 = 0
                }
            }
        }


    }
    fun vibrar(context: Context, duracion: Long) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Comprobamos si la versión del dispositivo es compatible con VibrationEffect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Si la versión del dispositivo es 26 o superior, usamos VibrationEffect
            vibrator.vibrate(VibrationEffect.createOneShot(duracion, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            // Para versiones anteriores a la 26, usamos la función deprecated vibrate()
            @Suppress("DEPRECATION")
            vibrator.vibrate(duracion)
        }
    }

    private fun intercambiarImagenesSiAdyacentes(boton1: Int, boton2: Int) {
        val imageButton1 = findViewById<ImageButton>(boton1)
        val imageButton2 = findViewById<ImageButton>(boton2)
        val movimientosText = findViewById<TextView>(R.id.puntos)
        val posicion1=imageButton1.tag;
        val posicion2=imageButton2.tag;
        Log.i("MYTAG2", posicion1.toString())
        Log.i("MYTAG2", posicion2.toString())
        val backgroundBoton1 = imageButton1.background
        val backgroundBoton2 = imageButton2.background
        if ((boton2 == (boton1 + 1)) || (boton2 ==(boton1 - 1)) || (boton2 ==(boton1 - COLUMNAS)) || (boton2 ==(boton1 + COLUMNAS))){
            imageButton1.background = backgroundBoton2
            imageButton2.background = backgroundBoton1
            val temp = graella[posicion1 as Int]

            movimientos=movimientos+1
            movimientosText.text = movimientos.toString()


            graella[posicion1] = graella[posicion2 as Int]
            graella[posicion2] = temp
            final()

        }
    }

    private fun mezclar() {
        val random = Random()
        var index: Int
        var temp: String
        for (i in graella.size - 1 downTo 0) {
            //hacemos un intercambio para mezclar las piezas, desordenando los números de la graella
            //como en el ejercicio de cifrado de M9
            index = random.nextInt(i + 1)
            temp = graella[index]
            graella[index] = graella[i]
            graella[i] = temp
        }
    }

    private fun inicializar() {
        //inicializamos la graella con numeros del 1 al 8 en este caso, en String
        graella = Array(COLUMNAS*COLUMNAS) { i -> i.toString() }
    }

    private fun estaResuelto(): Boolean {
        var resuelto = true
        for (i in graella.indices) {
            if (graella[i] != i.toString()) {
                resuelto = false
                break
            }
        }
        return resuelto
    }

    private fun final(){
        var exito=estaResuelto()
        if (exito){
            Log.i("resolucion", "si")
            finalNivell()
        }
    }
    private fun finalNivell(){
        var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://m8juego-e9538-default-rtdb.europe-west1.firebasedatabase.app/")
        var reference: DatabaseReference = database.getReference("DATA BASE JUGADORS")
        //captura la data
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateInstance()
        val formatedDate = formatter.format(date)
        var nivell : String ="2"
        ocultarbotones()
        var fondo:ImageView = findViewById(R.id.fondoniveles)
        fondo.setImageResource(R.drawable.win)
        //grava les dades del jugador (puntuació, nivell i Data)
        //accedint directament al punt del arbre de dades que volem anar, podem modificar
        //només una de les dades sense que calgui canviar tots els camps: nom, email...

        reference.child(UID).child("Puntuacio").setValue(movimientos.toString())
        reference.child(UID).child("Nivell").setValue(nivell)
        reference.child(UID).child("Data").setValue(formatedDate)


        continuarBtn.visibility = View.VISIBLE
    }
    private fun ocultarbotones(){
        val buttons = listOf(
            findViewById<ImageButton>(R.id.button),
            findViewById<ImageButton>(R.id.button2),
            findViewById<ImageButton>(R.id.button3),
            findViewById<ImageButton>(R.id.button4),
            findViewById<ImageButton>(R.id.button5),
            findViewById<ImageButton>(R.id.button6),
            findViewById<ImageButton>(R.id.button7),
            findViewById<ImageButton>(R.id.button8),
            findViewById<ImageButton>(R.id.button9)
        )
        for (button in buttons){
            button.visibility=View.INVISIBLE
        }
    }

}