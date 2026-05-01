package br.edu.utfpr.usandobd

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etCod: EditText
    private lateinit var etNome: EditText
    private lateinit var etTelefone: EditText

    private lateinit var banco: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etCod = findViewById(R.id.etCod)
        etNome = findViewById(R.id.etNome)
        etTelefone = findViewById(R.id.etTelefone)

        //CRIAMOS O BANCO DE DADOS
        banco = openOrCreateDatabase(
            "banco.db",
            MODE_PRIVATE,
            null
        )
        //CRIAMOS A TABELA
        banco.execSQL(
            "CREATE TABLE IF NOT EXISTS cadastro (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nome TEXT, telefone TEXT)"
        )
    }

    fun btIncluirOnClick(view: View) {
        val registro = ContentValues()
        registro.put( "nome", etNome.text.toString())
        registro.put( "telefone", etTelefone.text.toString())
        banco.insert("cadastro", null, registro )

        Toast.makeText(this, "Inclusão realizada com sucesso", Toast.LENGTH_SHORT).show()
    }

    fun btAlterarOnClick(view: View) {
        val registro = ContentValues()
        registro.put("nome", etNome.text.toString())
        registro.put("telefone", etTelefone.text.toString())

        banco.update(
            "cadastro",
            registro,
            "_id = " + etCod.text.toString(),
            null
        )

        Toast.makeText(this, "Alteração realizada com sucesso", Toast.LENGTH_SHORT).show()

    }
    fun btExcluirOnClick(view: View) {
        banco.delete( "cadastro", "_id = " + etCod.text.toString(), null)
        Toast.makeText(this, "Exclusão realizada com sucesso", Toast.LENGTH_SHORT).show()
    }
    fun btPesquisarOnClick(view: View) {

        val cursor = banco.query(
            "cadastro",
            null,
            "_id = " + etCod.text.toString(),
            null,
            null,
            null,
            null
        )

        if (cursor.moveToNext()) {
            etNome.setText(cursor.getString(1) )
            etTelefone.setText(cursor.getString(2) )
        } else {
            Toast.makeText(this, "Registro não encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    fun btListarOnClick(view: View) {

    }

} //fim da mainActivity