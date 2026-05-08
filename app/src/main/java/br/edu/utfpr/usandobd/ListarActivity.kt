package br.edu.utfpr.usandobd

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.usandobd.database.DatabaseHandler

class ListarActivity : AppCompatActivity() {

    private lateinit var lista: ListView
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

        val registros = banco.listarCursor()

        val adapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_1,
            registros,
            arrayOf( "nome" ),
            intArrayOf( android.R.id.text1),
            0
        )

        lista.adapter = adapter

/*        val registros = listOf( "Colombia", "Chile", "Brasil", "Argentina", "Paraguai", "Uruguai" )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            registros
        )

        lista.adapter = adapter

 */
    }
}