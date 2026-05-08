package br.edu.utfpr.usandobd

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.usandobd.database.DatabaseHandler
import br.edu.utfpr.usandobd.entity.Cadastro

class MainActivity : AppCompatActivity() {

    private lateinit var etCod: EditText
    private lateinit var etNome: EditText
    private lateinit var etTelefone: EditText

    private lateinit var banco: DatabaseHandler

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

        banco = DatabaseHandler(this)
    }

    fun btIncluirOnClick(view: View) {
        val cadastro = Cadastro(
            0,
            etNome.text.toString(),
            etTelefone.text.toString()
        )

        banco.inserir(cadastro)

        Toast.makeText(this, "Inclusão realizada com sucesso", Toast.LENGTH_SHORT).show()
    }

    fun btAlterarOnClick(view: View) {
        val cadastro = Cadastro(
            etCod.text.toString().toInt(),
            etNome.text.toString(),
            etTelefone.text.toString()
        )

        banco.alterar(cadastro)
        Toast.makeText(this, "Alteração realizada com sucesso", Toast.LENGTH_SHORT).show()

    }
    fun btExcluirOnClick(view: View) {
        banco.excluir(etCod.text.toString().toInt() )
        Toast.makeText(this, "Exclusão realizada com sucesso", Toast.LENGTH_SHORT).show()
    }
    fun btPesquisarOnClick(view: View) {

        val registro = banco.pesquisar(etCod.text.toString().toInt())

        if (registro!=null) {
            etNome.setText(registro.nome)
            etTelefone.setText(registro.telefone)
        } else {
            Toast.makeText(this, "Registro não encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    fun btListarOnClick(view: View) {


        val intent = Intent(this, ListarActivity::class.java)
        startActivity(intent)


        /*
        val registros = banco.listar()

        for( registro in registros ) {
            Toast.makeText(this, registro.nome, Toast.LENGTH_SHORT).show()
        }
        */
    }

} //fim da mainActivity