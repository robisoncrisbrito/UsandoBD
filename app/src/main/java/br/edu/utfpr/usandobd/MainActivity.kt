package br.edu.utfpr.usandobd

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.usandobd.database.DatabaseHandler
import br.edu.utfpr.usandobd.entity.Cadastro
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    private lateinit var etCod: EditText
    private lateinit var etNome: EditText
    private lateinit var etTelefone: EditText
    private lateinit var btSalvar: Button
    private lateinit var btExcluir: Button
    private lateinit var btPesquisar: Button

    //private lateinit var banco: DatabaseHandler
    val db = Firebase.firestore

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
        btSalvar = findViewById(R.id.btSalvar)
        btExcluir = findViewById(R.id.btExcluir)
        btPesquisar = findViewById(R.id.btPesquisar)

        //banco = DatabaseHandler(this)

        if ( intent.getIntExtra( "cod", 0 ) != 0 ) {
            etCod.setText( intent.getIntExtra( "cod", 0 ).toString() )
            etNome.setText( intent.getStringExtra("nome") )
            etTelefone.setText( intent.getStringExtra( "telefone" ) )
        } else {
            btExcluir.visibility = View.GONE
            btPesquisar.visibility = View.GONE
        }

    }

    fun btAlterarOnClick(view: View) {
        if ( etCod.text.toString().isEmpty() ) {
            /*val cadastro = Cadastro(
                0,
                etNome.text.toString(),
                etTelefone.text.toString()
            )
            banco.inserir(cadastro)*/
            val cadastro = hashMapOf(
                "nome" to etNome.text.toString(),
                "telefone" to etTelefone.text.toString()
            )

            db.collection( "Cadastro" )
                .document(etCod.text.toString())
                .set(cadastro)
                .addOnSuccessListener {
                    Toast.makeText(this, "Inclusão realizada com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro na Inclusão", Toast.LENGTH_SHORT).show()
                }


        } else {
            /*val cadastro = Cadastro(
                etCod.text.toString().toInt(),
                etNome.text.toString(),
                etTelefone.text.toString()
            )

            banco.alterar(cadastro)
             */

            val cadastro = hashMapOf(
                "nome" to etNome.text.toString(),
                "telefone" to etTelefone.text.toString()
            )

            db.collection( "Cadastro" )
                .document(etCod.text.toString())
                .set(cadastro)
                .addOnSuccessListener {
                    Toast.makeText(this, "Inclusão realizada com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro na Inclusão", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun btExcluirOnClick(view: View) {
        //banco.excluir(etCod.text.toString().toInt() )

        db.collection( "Cadastro" )
            .document( etCod.text.toString())
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Exclusão realizada com sucesso", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro na Exclusão", Toast.LENGTH_SHORT).show()
            }
    }

    fun btPesquisarOnClick(view: View) {

        /*val registro = banco.pesquisar(etCod.text.toString().toInt())

        if (registro!=null) {
            etNome.setText(registro.nome)
            etTelefone.setText(registro.telefone)
        } else {
            Toast.makeText(this, "Registro não encontrado", Toast.LENGTH_SHORT).show()
        }*/

        db.collection( "Cadastro" )
            .document( etCod.text.toString() )
            .get()
            .addOnSuccessListener { result ->
                if ( result.data == null ) {
                    Toast.makeText(this, "Registro não encontrado", Toast.LENGTH_SHORT).show()
                } else {
                    etNome.setText( result.data?.get( "nome").toString() )
                    etTelefone.setText( result.data?.get( "telefone").toString() )
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro na Exclusão", Toast.LENGTH_SHORT).show()
            }

    }

} //fim da mainActivity