package com.example.juegoM8

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.juegoM8.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    //per a comprovar si la sessió esta inicialitzada
    lateinit var auth: FirebaseAuth
    var user: FirebaseUser? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //assigna valor a user
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser

        var LOGIN = findViewById<Button>(R.id.login);
        var REGISTRO = findViewById<Button>(R.id.registro);
        val tf = Typeface.createFromAsset(assets,"fonts/Pulang.ttf")
        REGISTRO.setTypeface(tf)
        LOGIN.setTypeface(tf)
        LOGIN.setOnClickListener() {
            Toast.makeText(this, "click boton login", Toast.LENGTH_LONG).show();
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        REGISTRO.setOnClickListener() {
            Toast.makeText(this, "click boton Registro", Toast.LENGTH_LONG).show();
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }

    // Aquest mètode s'executarà quan s'obri el menu
    override fun onStart() {
        usuariLogejat()
        super.onStart()
    }

    //Si l'usuari s'ha logejat va al Menu
    private fun usuariLogejat() {
        if (user != null) {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }

    }
}