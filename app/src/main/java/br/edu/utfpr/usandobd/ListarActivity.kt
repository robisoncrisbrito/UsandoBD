package br.edu.utfpr.usandobd

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.usandobd.adapter.ElementoListaAdapter
import br.edu.utfpr.usandobd.database.DatabaseHandler
import br.edu.utfpr.usandobd.entity.Cadastro
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ListarActivity : AppCompatActivity() {

    private lateinit var lista: ListView
    private lateinit var fabAdicionar: FloatingActionButton
    private lateinit var banco: DatabaseHandler

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.listar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        banco = DatabaseHandler(this)
        lista = findViewById(R.id.listar)
        fabAdicionar = findViewById(R.id.fabAdicionar)

        fabAdicionar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()

        db.collection( "Cadastro" )
            .get()
            .addOnSuccessListener { result ->
                val registros = mutableListOf<Cadastro>()

                for ( document in result ) {
                    val cadastro = Cadastro(
                        document.id.toString().toInt(),
                        document.data?.get( "nome" ).toString(),
                        document.data?.get( "telefone").toString()
                    )

                    registros.add( cadastro )
                }

                val adapter = ElementoListaAdapter( this, registros )
                lista.adapter = adapter

            }
            .addOnFailureListener {
                Toast.makeText( this, "Erro ao carregar a listagem", Toast.LENGTH_LONG ).show()
            }


        //val registros = banco.listarCursor()

    }
}