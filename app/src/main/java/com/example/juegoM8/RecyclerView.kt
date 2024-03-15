package com.example.juegoM8

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
                        /*val nom = jugadorSnapshot.child("Nom").getValue(Jugador::class.java)
                        val puntuacio = jugadorSnapshot.child("Puntuacio").getValue(String::class.java)
                        val imatgeUrl = jugadorSnapshot.child("Imatge").getValue(String::class.java) // URL de la imagen en Firebase Storage

                        // Crear un objeto Jugador con los datos recuperados
                        val jugador = Jugador(nom ?: "", puntuacio ?: "", imatgeUrl ?: "")
                        jugadorArrayList.add(jugador)

                        // Notificar al adaptador que los datos han cambiado
                        jugadorRecyclerView.adapter?.notifyDataSetChanged()

                        // Descargar la imagen de Firebase Storage y actualizar el ImageView cuando la descarga sea exitosa
                        val storageRef = FirebaseStorage.getInstance().reference
                        val imageRef = storageRef.child(imatgeUrl ?: "")
                        val ONE_MEGABYTE: Long = 1024 * 1024
                        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { imageData ->
                            // Convertir los datos de la imagen en un Bitmap y establecerlo en el ImageView
                            val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
                            // Actualizar la imagen en el RecyclerView
                            jugador.imatgeBitmap = bitmap
                            jugadorRecyclerView.adapter?.notifyDataSetChanged()
                        }.addOnFailureListener {
                            // Manejar cualquier error de descarga de imagen
                        }*/
                    }
                    // Ordenar la lista de jugadores por puntuación en orden descendente
                    jugadorArrayList.sortByDescending { it.Puntuacio.toIntOrNull() ?: 0 }
                    jugadorRecyclerView.adapter = JugadorsAdapter(jugadorArrayList)
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