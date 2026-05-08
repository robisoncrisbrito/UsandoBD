package br.edu.utfpr.usandobd.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.utfpr.usandobd.entity.Cadastro

class DatabaseHandler(contexto: Context) : SQLiteOpenHelper(
    contexto,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    override fun onCreate(banco: SQLiteDatabase?) {
        banco?.execSQL("CREATE TABLE IF NOT EXISTS ${TABLE_NAME} (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nome TEXT, telefone TEXT, email TEXT)")
    }

    override fun onUpgrade(
        banco: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        banco?.execSQL( "DROP TABLE IF EXISTS ${TABLE_NAME}")
        onCreate(banco)
    }

    fun inserir( cadastro: Cadastro ) {
        val banco = writableDatabase

        val registro = ContentValues()
        registro.put( "nome", cadastro.nome)
        registro.put( "telefone", cadastro.telefone)
        banco.insert(TABLE_NAME, null, registro )

    }

    fun alterar( cadastro: Cadastro ) {
        val banco = writableDatabase

        val registro = ContentValues()
        registro.put("nome", cadastro.nome)
        registro.put("telefone", cadastro.telefone)

        banco.update(
            TABLE_NAME,
            registro,
            "_id = " + cadastro.id,
            null
        )
    }

    fun excluir( id: Int ) {
        val banco = writableDatabase

        banco.delete( TABLE_NAME, "_id = " + id, null)
    }

    fun pesquisar( id: Int ): Cadastro? {

        val banco = writableDatabase

        val cursor = banco.query(
            TABLE_NAME,
            null,
            "_id = " + id,
            null,
            null,
            null,
            null
        )

        if (cursor.moveToNext()) {
            val cadastro = Cadastro(
                cursor.getInt(ID),
                cursor.getString(NOME),
                cursor.getString(TELEFONE)
            )

            return cadastro
        } else {
            return null
        }
    }

    fun listar(): MutableList<Cadastro> {
        val banco = writableDatabase

        val saida = mutableListOf<Cadastro>()

        val cursor = banco.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while( cursor.moveToNext()) {
            val cadastro = Cadastro(
                cursor.getInt( ID),
                cursor.getString( NOME ),
                cursor.getString( TELEFONE )
            )

            saida.add( cadastro )
        }

        return saida

    }

    fun listarCursor(): Cursor {

        val banco = writableDatabase

        val cursor = banco.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        return cursor

    }

    companion object {
        private const val DATABASE_NAME = "banco.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "cadastro"
        private const val ID = 0
        private const val NOME = 1
        private const val TELEFONE = 2
    }

}