package com.example.holafirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = Firebase.database.reference //acceder a la reiz de la base de datos

        val listener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) { //cuando hay un cambio hace el evento
                val data = snapshot.getValue(String::class.java)
                findViewById<TextView>(R.id.tvData).text = "Firebase remote: $data"
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        val dataRef = database.child("hola_firebase")//nombre del hijo de la raiz (rama)
            .child("data")//el hijo de la rama (lo llamaremos hoja)

        dataRef.addValueEventListener(listener)

        val btnSent = findViewById<MaterialButton>(R.id.btnSent) //crear valiable para el btnSent
        btnSent.setOnClickListener { //evento para el boton al hacer click
            val data = findViewById<TextInputEditText>(R.id.etData).text.toString()
            dataRef.setValue(data) //remplazar lo que existe en la hoja por el data
        }

        btnSent.setOnLongClickListener { //evento para cuendo se mantiene pulsado
            dataRef.removeValue() //se elimina el valor de la hoja, si solo hay una se elimina toda la rama
            true
        }
    }
}