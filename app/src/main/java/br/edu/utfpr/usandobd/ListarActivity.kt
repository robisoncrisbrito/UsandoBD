package br.edu.utfpr.usandobd

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.usandobd.adapter.ElementoListaAdapter
import br.edu.utfpr.usandobd.database.DatabaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListarActivity : AppCompatActivity() {

    private lateinit var lista: ListView
    private lateinit var fabAdicionar: FloatingActionButton
    private lateinit var banco: DatabaseHandler

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

        val registros = banco.listarCursor()
        val adapter = ElementoListaAdapter( this, registros )
        lista.adapter = adapter

    }
}