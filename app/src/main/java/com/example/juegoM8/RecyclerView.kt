package com.example.juegoM8

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juegoM8.adapter.JugadorsAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class RecyclerView : AppCompatActivity() {

    private lateinit var dbref: DatabaseReference
    private lateinit var jugadorArrayList: ArrayList<Jugador>
    private lateinit var jugadorRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recicler_view)
        initRecyclerView()
    }

    fun initRecyclerView(){
        jugadorRecyclerView = findViewById(R.id.RecyclerOne)
        jugadorRecyclerView.layoutManager= LinearLayoutManager(this)
        jugadorArrayList = arrayListOf<Jugador>()
        getJugadorData()
    }

    private fun getJugadorData() {
        dbref = FirebaseDatabase.getInstance("https://juegom8-d97f7-default-rtdb.firebaseio.com/").getReference("DATA BASE JUGADORS")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    jugadorArrayList.clear() // Limpiar la lista antes de agregar los datos
                    for (jugadorSnapshot in snapshot.children) {
                        val jugador = jugadorSnapshot.getValue(Jugador::class.java)
                        jugador?.let {
                            jugadorArrayList.add(it) // Agregar el jugador a la lista
                        }

                    }

                    var adapter = JugadorsAdapter(jugadorArrayList)

                    // Ordenar la lista de jugadores por puntuación en orden descendente
                    jugadorArrayList.sortByDescending { it.Puntuacio.toIntOrNull() ?: 0 }
                    jugadorRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object : JugadorsAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            Toast.makeText(this@RecyclerView, "Has clicat a .$position", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@RecyclerView, DetalleJugador::class.java)
                            intent.putExtra("Nom", jugadorArrayList[position].Nom)
                            intent.putExtra("Puntuacio", jugadorArrayList[position].Puntuacio)
                            intent.putExtra("Imatge", jugadorArrayList[position].Imatge)
                            intent.putExtra("Data", jugadorArrayList[position].Data)
                            intent.putExtra("Edat", jugadorArrayList[position].Edat)
                            intent.putExtra("Email", jugadorArrayList[position].Email)
                            intent.putExtra("Poblacio", jugadorArrayList[position].Poblacio)
                            startActivity(intent) // Iniciar la actividad

                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar error de cancelación
                TODO("Not yet implemented")
            }
        })
    }


    fun onItemSelected(jugador: Jugador){
        Toast.makeText(this, jugador.Nom, Toast.LENGTH_SHORT).show()
    }
}