package com.example.juegoM8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juegoM8.adapter.JugadorsAdapter

class RecyclerView : AppCompatActivity() {

    val jugadors = listOf<Jugador>(
        Jugador("Pepe",12,"https://www.kasandbox.org/programming-images/avatars/piceratops-tree.png"),
        Jugador("Juan",20,"https://www.kasandbox.org/programming-images/avatars/leafers-seed.png"),
        Jugador("Luis",15,"https://www.kasandbox.org/programming-images/avatars/leaf-yellow.png"),
        Jugador("Jorge",32,"https://www.kasandbox.org/programming-images/avatars/leaf-blue.png"),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recicler_view)
        initRecyclerView()
    }

    fun initRecyclerView(){
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        val recyclerView=findViewById<RecyclerView>(R.id.RecyclerOne)
        recyclerView.layoutManager= manager
        recyclerView.adapter=
            JugadorsAdapter(jugadors) {jugador ->
                onItemSelected(
                    jugador
                )
            }
        recyclerView.addItemDecoration(decoration)
    }

    fun onItemSelected(jugador: Jugador){
        Toast.makeText(this, jugador.nom_jugador, Toast.LENGTH_SHORT).show()
    }
}