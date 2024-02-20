package com.example.juegoM8

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.ImageView
import com.example.juegoM8.R
import java.util.Timer
import kotlin.concurrent.schedule

class Splash : AppCompatActivity() {
    private val duracio: Long = 5000;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //amaguem la barra, pantalla a full
        supportActionBar?.hide()
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //val logo = findViewById<ImageView>(R.id.imageView)

        //Glide.with(this).load(R.drawable.imagendiferencia).into(logo)

        //cridem a la funció de canviar activitat
        canviarActivity()
    }

    private fun canviarActivity() {
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, duracio)

    }

    }