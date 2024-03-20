package com.example.juegoM8

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import java.util.Timer
import java.util.TimerTask

class Credits : AppCompatActivity() {
    var timer= Timer()
    lateinit var menuBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)
        timer.scheduleAtFixedRate(TimeTask(),0L,3000L)
        menuBtn = findViewById<Button>(R.id.button22)
        menuBtn.setOnClickListener(){
            val intent = Intent (this, Menu::class.java)
            startActivity(intent)
        }
    }
    private inner class TimeTask: TimerTask(){
        private var numeroFragment:Int=1;
        override fun run() {
            numeroFragment++
            if (numeroFragment>2) numeroFragment=1
            if (numeroFragment==1) {
                supportFragmentManager.commit {
                    replace<Centro>(R.id.fragmentcontainer)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
            }
            else {
                supportFragmentManager.commit {
                    replace<Alumnas>(R.id.fragmentcontainer)
                    setReorderingAllowed(true)
                    addToBackStack("replacement")
                }
            }
        }
    }

}