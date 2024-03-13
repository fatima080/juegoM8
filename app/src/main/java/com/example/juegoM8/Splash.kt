package com.example.juegoM8

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.ImageView
import com.example.juegoM8.R
import java.util.Timer
import kotlin.concurrent.schedule
import pl.droidsonroids.gif.GifImageView

class Splash : AppCompatActivity() {
    private val duracio: Long = 3000;
    lateinit var mediaplayer: MediaPlayer;
    lateinit var gifImageView: GifImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //amaguem la barra, pantalla a full
        mediaplayer = MediaPlayer.create(getBaseContext(),R.raw.audioinicio)
        mediaplayer.start()
        supportActionBar?.hide()
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        gifImageView = findViewById(R.id.imageView)

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