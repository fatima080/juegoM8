package com.example.juegoM8

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class Menu : AppCompatActivity() {
    //creem unes variables per comprovar ususari i authentificació
    lateinit var auth: FirebaseAuth
    var user: FirebaseUser? = null;
    lateinit var tancarSessio: Button
    lateinit var CreditsBtn: Button
    lateinit var PuntuacionsBtn: Button
    lateinit var jugarBtn: Button
    lateinit var editarBtn: Button
    lateinit var miPuntuaciotxt: TextView
    lateinit var puntuacio: TextView
    lateinit var uid: TextView
    lateinit var correo: TextView
    lateinit var nom: TextView
    private var nivell ="1"
    lateinit var edat: TextView
    lateinit var poblacio: TextView
    lateinit var imatgePerfil: ImageView
    lateinit var imatgeUri: Uri
    // Variables per a gravar a Storage
    lateinit var storageReference: StorageReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        tancarSessio =findViewById<Button>(R.id.tancarSessio)
        CreditsBtn =findViewById<Button>(R.id.CreditsBtn)
        PuntuacionsBtn =findViewById<Button>(R.id.PuntuacionsBtn)
        jugarBtn =findViewById<Button>(R.id.jugarBtn)
        val tf = Typeface.createFromAsset(assets,"fonts/Pulang.ttf")
        auth= FirebaseAuth.getInstance()
        user =auth.currentUser
        //busquem els textos
        miPuntuaciotxt=findViewById(R.id.miPuntuaciotxt)
        puntuacio=findViewById(R.id.puntuacio)
        uid=findViewById(R.id.uid)
        correo=findViewById(R.id.correo2)
        nom=findViewById(R.id.nom)
        editarBtn = findViewById<Button>(R.id.editarBtn)
        edat=findViewById(R.id.edat)
        poblacio=findViewById(R.id.poblacio)
        imatgePerfil=findViewById(R.id.fireworks)
        //Inicialitza el StorageReference
        storageReference = FirebaseStorage.getInstance().getReference()
        //els hi assignem el tipus de lletra
        miPuntuaciotxt.setTypeface(tf)
        puntuacio.setTypeface(tf)
        uid.setTypeface(tf)
        correo.setTypeface(tf)
        nom.setTypeface(tf)
        editarBtn.setTypeface(tf)
        poblacio.setTypeface(tf)
        edat.setTypeface(tf)
        //fem el mateix amb el text dels botons
        tancarSessio.setTypeface(tf)
        CreditsBtn.setTypeface(tf)
        PuntuacionsBtn.setTypeface(tf)
        jugarBtn.setTypeface(tf)
        consulta()

        tancarSessio.setOnClickListener(){
            tancalaSessio()
        }
        editarBtn.setOnClickListener(){
            Toast.makeText(this,"EDITAR", Toast.LENGTH_SHORT).show()
            canviaLaImatge()
        }

        CreditsBtn.setOnClickListener(){
            Toast.makeText(this,"Credits", Toast.LENGTH_SHORT).show()
        }
        PuntuacionsBtn.setOnClickListener(){
            Toast.makeText(this,"Puntuacions", Toast.LENGTH_SHORT).show()
            val intent= Intent(this, RecyclerView::class.java)
            startActivity(intent)

        }
        jugarBtn.setOnClickListener(){
            var Uids : String = uid.getText().toString()
            var noms : String = nom.getText().toString()
            var puntuacios : String = puntuacio.getText().toString()
            var nivells : String =nivell
            val intent= Intent(this, seleccionivell::class.java)
            intent.putExtra("UID",Uids)
            intent.putExtra("NOM",noms)
            intent.putExtra("PUNTUACIO",puntuacios)
            intent.putExtra("NIVELL",nivells)
            startActivity(intent)
            finish()
        }



    }
    override fun onStart() {
        usuariLogejat()
        super.onStart()
    }
    private fun usuariLogejat()
    {
        if (user !=null)
        {
            Toast.makeText(this,"Jugador logejat",
                Toast.LENGTH_SHORT).show()
        }
        else
        {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun tancalaSessio() {
        auth.signOut() //tanca la sessió
        //va a la pantalla inicial
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun consulta(){
        var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://juegom8-d97f7-default-rtdb.firebaseio.com/")
        var bdreference:DatabaseReference = database.getReference("DATA BASE JUGADORS")
        bdreference.addValueEventListener (object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i ("DEBUG","arrel value"+
                        snapshot.getValue().toString())
                Log.i ("DEBUG","arrel key"+ snapshot.key.toString())
                // ara capturem tots els fills
                var trobat:Boolean =false
                for (ds in snapshot.getChildren()) {
                    Log.i ("DEBUG","DS key: "+ds.child("Uid").key.toString())
                    Log.i ("DEBUG","DS value: "+ds.child("Uid").getValue().toString())
                    Log.i ("DEBUG","DS data:  "+ds.child("Data").getValue().toString())
                    Log.i ("DEBUG","DS mail:  "+ds.child("Email").getValue().toString())
                    //mirem si el mail és el mateix que el del jugador
                    //si és així, mostrem les dades als textview corresponents
                    //mirem si el mail és el mateix que el del jugador
                    //si és així, mostrem les dades als textview corresponents
                    if (ds.child("Email").getValue(String::class.java)?.equals(user?.email, ignoreCase = true) == true) {
                        trobat = true
                        //carrega els textview
                        puntuacio.setText(ds.child("Puntuacio").getValue().toString())
                        uid.setText(ds.child("Uid").getValue().toString())
                        correo.setText(ds.child("Email").getValue().toString())
                        nom.setText(ds.child("Nom").getValue().toString())
                        nivell = ds.child("Nivell").getValue().toString()
                        poblacio.setText(ds.child("Poblacio").getValue().toString())
                        edat.setText(ds.child("Edat").getValue().toString())
                        var imatge: String = ds.child("Imatge").getValue().toString()
                        try {
                            Picasso.get().load(imatge).into(imatgePerfil)
                        } catch (e: Exception) {
                            Picasso.get().load(R.drawable.userimage).into(imatgePerfil)
                        }
                    }
                    if (!trobat)
                    {
                        Log.e ("ERROR","ERROR NO TROBAT MAIL")
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e ("ERROR","ERROR DATABASE CANCEL")
            }
        })
    }

    private fun canviaLaImatge() {
        //utilitzarem un alertdialog que seleccionara de galeria o agafar una foto
                // Si volem fer un AlertDialog amb més de dos elements (amb una llista),
        // Aixó ho fariem amb fragments (que veurem més endevant)
        // Aquí hi ha un tutorial per veure com es fa:
        // https://www.codevscolor.com/android-kotlin-list-alert-dialog
        //Veiem com es crea un de dues opcions (habitualment acceptar o cancel·lar:
        val dialog = AlertDialog.Builder(this)
            .setTitle("CANVIAR IMATGE")
            .setMessage("Seleccionar imatge de: ")
            .setNegativeButton("Galeria") { view, _ ->
                Toast.makeText(this, "De galeria",
                    Toast.LENGTH_SHORT).show()
                //mirem primer si tenim permisos per a accedir a Read External Storage
                        if (askForPermissions()) {
                            // Permissions are already granted, do your stuff
                            // Ara agafarem una imatge de la galeria
                            val intent = Intent(Intent.ACTION_PICK)
                            val REQUEST_CODE=201 //Aquest codi és un número que faremservir per
                            // a identificar el que hem recuperat del intent
                                    // pot ser qualsevol número
                                    intent.type = "image/*"
                            startActivityForResult(intent, REQUEST_CODE)
                        }
                        else{
                            Toast.makeText(this, "ERROR PERMISOS",
                                Toast.LENGTH_SHORT).show()
                        }
            }
            .setPositiveButton("Càmera") { view, _ ->
                if (checkCameraPermissions()) {
                    // Abrir la cámara
                    openCamera()
                } else {
                    // Solicitar permisos si no están concedidos
                    requestCameraPermissions()
                }
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }

    private fun checkCameraPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermissions() {
        val CAMERA_PERMISSION_REQUEST_CODE = 101
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    private fun openCamera() {
        val CAMERA_REQUEST_CODE = 102
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val REQUEST_CODE = 201
        val CAMERA_REQUEST_CODE = 102

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            imatgeUri = data?.data!!
            imatgePerfil.setImageURI(imatgeUri)
            pujarFoto(imatgeUri)
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            // La imagen de la cámara se almacena en el intent y se puede obtener como un extra llamado "data"
            val imageBitmap = data?.extras?.get("data") as Bitmap

            // Convierte el Bitmap en un Uri
            val tempUri = getImageUri(applicationContext, imageBitmap)

            imatgePerfil.setImageURI(tempUri)
            pujarFoto(tempUri)
        } else {
            Toast.makeText(
                this, "Error recuperant imatge de galeria",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Función para obtener el Uri de un Bitmap
    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    private fun pujarFoto(imatgeUri: Uri) {
        try {
            var folderReference: StorageReference =
                storageReference.child("FotosPerfil")
            var Uids: String = uid.text.toString()

            // Subir la imagen al Storage de Firebase
            val uploadTask = folderReference.child(Uids).putFile(imatgeUri)

            // Listener para saber cuando se completa la subida de la imagen
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                // Continuar con la operación para obtener la URL de la imagen
                folderReference.child(Uids).downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Obtener la URL de la imagen
                    val downloadUri = task.result
                    // Guardar la URL de la imagen en la variable imatge
                    var imatge = downloadUri.toString()
                    // Actualizar el campo "Imatge" en la base de datos
                    var database: FirebaseDatabase =
                        FirebaseDatabase.getInstance("https://juegom8-d97f7-default-rtdb.firebaseio.com/")
                    var reference: DatabaseReference =
                        database.getReference("DATA BASE JUGADORS")
                    reference.child(Uids).child("Imatge").setValue(imatge)
                    // Notificar al usuario que la imagen se ha subido correctamente
                    Toast.makeText(
                        this, "Imagen subida correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Handle failures
                    Toast.makeText(
                        this, "Error subiendo la imagen",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        } catch (e: Exception) {
            Log.e("ERROR", "Error al subir la imagen: ${e.message}", e)
            Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
        }
    }



    //----------------------------------------Permisos----------------
    fun isPermissionsAllowed(): Boolean {
        return if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            false
        } else true
    }
    fun askForPermissions(): Boolean {
        val REQUEST_CODE=201
        if (!isPermissionsAllowed()) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this ,android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(this ,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_CODE)
            }
            return false
        }
        return true
    }
    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions,
            grantResults)
        val REQUEST_CODE=201
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission is granted, you can perform your operation here
                } else {
                    // permission is denied, you can ask for permission again, if you want
                    // askForPermissions()
                }
                return
            }
        }
    }
    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton("App Settings", DialogInterface.OnClickListener { dialogInterface, i ->
                        // send to app settings if permission is denied permanently
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", getPackageName(), null)
                        intent.data = uri
                        startActivity(intent)
                    })
                .setNegativeButton("Cancel",null).show()
    }
//----------------------------------------------------------------


}