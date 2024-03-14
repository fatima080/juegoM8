package com.example.juegoM8

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.ImageButton
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
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
//lateinit var tf: Typeface

class Nivel3 : AppCompatActivity() {

    var soundPool: SoundPool? = null
    var movimientoSoundId=0
    var victoriaSoundId=0

    private val COLUMNAS = 4
    private val FILAS = 4
    private var botonpulsado1 : Int=0;
    private var botonpulsado2 : Int=0;
    private var movimientos: Int=0;
    lateinit var continuarBtn: Button
    private lateinit var graella: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nivel3)
        var intent:Bundle? = intent.extras
        UID = intent?.get("UID").toString()
        NOM = intent?.get("NOM").toString()
        PUNTUACIO = intent?.get("PUNTUACIO").toString()
        NIVELL = intent?.get("NIVELL").toString()
        continuarBtn = findViewById(R.id.continuarBtn)
        val tf = Typeface.createFromAsset(assets,"fonts/Pulang.ttf")
        continuarBtn.setTypeface(tf)
        continuarBtn.visibility = View.INVISIBLE
        continuarBtn.setOnClickListener(){
            //va a la pantalla inicial
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        inicializar()
        inicializarSoundPool()
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
            findViewById<ImageButton>(R.id.button9),
            findViewById<ImageButton>(R.id.button10),
            findViewById<ImageButton>(R.id.button11),
            findViewById<ImageButton>(R.id.button12),
            findViewById<ImageButton>(R.id.button13),
            findViewById<ImageButton>(R.id.button14),
            findViewById<ImageButton>(R.id.button15),
            findViewById<ImageButton>(R.id.button16)
        )

        for (i in graella.indices) {
            val button = buttons[i]

            // Configuración del fondo del botón según el valor en graella[i]
            when (graella[i]) {
                "0" -> button.setBackgroundResource(R.drawable.gatoenricuno)
                "1" -> button.setBackgroundResource(R.drawable.gatoenricdos)
                "2" -> button.setBackgroundResource(R.drawable.gatoenrictres)
                "3" -> button.setBackgroundResource(R.drawable.gatoenriccuatro)
                "4" -> button.setBackgroundResource(R.drawable.gatoenriccinco)
                "5" -> button.setBackgroundResource(R.drawable.gatoenricseis)
                "6" -> button.setBackgroundResource(R.drawable.gatoenricsiete)
                "7" -> button.setBackgroundResource(R.drawable.gatoenricocho)
                "8" -> button.setBackgroundResource(R.drawable.gatoenricnueve)
                "9" -> button.setBackgroundResource(R.drawable.gatoenricdiez)
                "10" -> button.setBackgroundResource(R.drawable.gatoenriconce)
                "11" -> button.setBackgroundResource(R.drawable.gatoenricdoce)
                "12" -> button.setBackgroundResource(R.drawable.gatoenrictrece)
                "13" -> button.setBackgroundResource(R.drawable.gatoenriccatorce)
                "14" -> button.setBackgroundResource(R.drawable.gatoenricquince)
                "15" -> button.setBackgroundResource(R.drawable.gatoenricdieziseis)
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
        val posicion1 = imageButton1.tag as Int
        val posicion2 = imageButton2.tag as Int

        Log.i("POSICION1", posicion1.toString())
        Log.i("POSICION1", posicion2.toString())

        val fila1 = posicion1 / COLUMNAS
        val columna1 = posicion1 % COLUMNAS
        val fila2 = posicion2 / COLUMNAS
        val columna2 = posicion2 % COLUMNAS
        Log.i("FILAYCOLUMNA", fila1.toString())
        Log.i("FILAYCOLUMNA", fila2.toString())
        Log.i("FILAYCOLUMNA", columna1.toString())
        Log.i("FILAYCOLUMNA", columna2.toString())

        if ((fila1 == fila2 && (columna1 - columna2 == 1 || columna2 - columna1 == 1)) ||
            (columna1 == columna2 && (fila1 - fila2 == 1 || fila2 - fila1 == 1))
        ) {
            // Los botones son adyacentes, puedes intercambiar imágenes
            val backgroundBoton1 = imageButton1.background
            val backgroundBoton2 = imageButton2.background
            imageButton1.background = backgroundBoton2
            imageButton2.background = backgroundBoton1

            val temp = graella[posicion1]
            graella[posicion1] = graella[posicion2]
            graella[posicion2] = temp

            movimientos++
            movimientosText.text = movimientos.toString()

            //final()
            if (!final()){
                reproducirSonido("movimiento")
            }
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
        graella = Array(COLUMNAS*FILAS) { i -> i.toString() }
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

    private fun final() : Boolean{
        var exito=estaResuelto()
        if (exito){
            Log.i("resolucion", "si")
            finalNivell()
        }
        return exito;
    }
    private fun finalNivell(){
        var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://juegom8-d97f7-default-rtdb.firebaseio.com/")
        var reference: DatabaseReference = database.getReference("DATA BASE JUGADORS")
        //captura la data
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateInstance()
        val formatedDate = formatter.format(date)
        var nivell : String ="4"
        ocultarbotones()
        var fondo:ImageView = findViewById(R.id.fondoniveles)
        fondo.setImageResource(R.drawable.win)
        reproducirSonido("victoria")
        //grava les dades del jugador (puntuació, nivell i Data)
        //accedint directament al punt del arbre de dades que volem anar, podem modificar
        //només una de les dades sense que calgui canviar tots els camps: nom, email...

        reference.child(UID).child("Puntuacio").setValue((PUNTUACIO.toInt()+movimientos).toString())
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
            findViewById<ImageButton>(R.id.button9),
            findViewById<ImageButton>(R.id.button10),
            findViewById<ImageButton>(R.id.button11),
            findViewById<ImageButton>(R.id.button12),
            findViewById<ImageButton>(R.id.button13),
            findViewById<ImageButton>(R.id.button14),
            findViewById<ImageButton>(R.id.button15),
            findViewById<ImageButton>(R.id.button16)

        )
        for (button in buttons){
            button.visibility=View.INVISIBLE
        }
    }

    private fun inicializarSoundPool() {
        soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(audioAttributes)
                .build()
        } else {
            SoundPool(2, AudioManager.STREAM_MUSIC, 0)
        }

        // Cargar los sonidos en el SoundPool
        movimientoSoundId = soundPool!!.load(this, R.raw.movimiento, 1)
        victoriaSoundId = soundPool!!.load(this, R.raw.happyend, 1)
    }
    private fun reproducirSonido(evento: String){
        when (evento) {
            "movimiento" -> {
                // Reproducir el sonido de movimiento
                soundPool?.play(movimientoSoundId, 1.0f, 1.0f, 0, 0, 1.0f)
            }
            "victoria" -> {
                // Reproducir el sonido de victoria
                soundPool?.play(victoriaSoundId, 1.0f, 1.0f, 0, 0, 1.0f)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        // Liberar el SoundPool cuando la actividad se destruya
        soundPool?.release()
    }

}