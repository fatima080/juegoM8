package com.example.juegoM8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.juegoM8.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var LOGIN = findViewById<Button>(R.id.login);
        var REGISTRO = findViewById<Button>(R.id.registro);
        LOGIN.setOnClickListener(){
            Toast.makeText(this, "click boton login",Toast.LENGTH_LONG).show();
        }
        REGISTRO.setOnClickListener(){
            Toast.makeText(this, "click boton Registro",Toast.LENGTH_LONG).show();
        }
}
}