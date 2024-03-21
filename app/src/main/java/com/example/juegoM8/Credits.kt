package com.example.juegoM8

import android.content.Intent
import android.graphics.Typeface
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
        val tf = Typeface.createFromAsset(assets,"fonts/Pulang.ttf")
        menuBtn = findViewById<Button>(R.id.button22)
        menuBtn.setTypeface(tf)
        timer.scheduleAtFixedRate(TimeTask(),0L,3000L)
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
                if (!supportFragmentManager.isStateSaved) {
                    supportFragmentManager.commit {
                        replace<Centro>(R.id.fragmentcontainer)
                        setReorderingAllowed(true)
                        addToBackStack("replacement")
                    }
                }
            }
            else {
                if (!supportFragmentManager.isStateSaved) {
                    supportFragmentManager.commit {
                        replace<Alumnas>(R.id.fragmentcontainer)
                        setReorderingAllowed(true)
                        addToBackStack("replacement")
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }



}