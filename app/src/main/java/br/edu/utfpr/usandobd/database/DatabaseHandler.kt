package br.edu.utfpr.usandobd.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.utfpr.usandobd.entity.Cadastro

class DatabaseHandler(contexto: Context) : SQLiteOpenHelper(
    contexto,
    "banco.db",
    null,
    2
) {
    override fun onCreate(banco: SQLiteDatabase?) {
        banco?.execSQL("CREATE TABLE IF NOT EXISTS cadastro (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nome TEXT, telefone TEXT, email TEXT)")
    }

    override fun onUpgrade(
        banco: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        banco?.execSQL( "DROP TABLE IF EXISTS cadastro")
        onCreate(banco)
    }

    fun inserir( cadastro: Cadastro ) {
        val banco = writableDatabase

        val registro = ContentValues()
        registro.put( "nome", cadastro.nome)
        registro.put( "telefone", cadastro.telefone)
        banco.insert("cadastro", null, registro )

    }


}