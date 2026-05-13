package br.edu.utfpr.usandobd.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import br.edu.utfpr.usandobd.R
import br.edu.utfpr.usandobd.entity.Cadastro

class ElementoListaAdapter(val context: Context, val cursor: Cursor) : BaseAdapter() {

    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(pos: Int): Any? {
        cursor.moveToPosition( pos )

        val cadastro = Cadastro(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2)
        )

        return cursor.getInt(0).toLong()
    }

    override fun getItemId(pos: Int): Long {

        cursor.moveToPosition( pos )
        return cursor.getInt(0).toLong()

    }

    override fun getView(
        pos: Int,
        p1: View?,
        p2: ViewGroup?
    ): View? {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.elemento_lista, null )

        val tvNomeElementoLista = v.findViewById<TextView>( R.id.tvNomeElementoLista )
        val tvTelefoneElementoLista = v.findViewById<TextView>(R.id.tvTelefoneElementoLista )
        val btEditarElementoLista = v.findViewById<ImageButton>(R.id.btEditarElementoLista)

        cursor.moveToPosition( pos )

        tvNomeElementoLista.text = cursor.getString( 1 )
        tvTelefoneElementoLista.text = cursor.getString( 2 )

        btEditarElementoLista.setOnClickListener {
            Toast.makeText( context, "elemento ${pos}", Toast.LENGTH_LONG ).show()
        }

        return v
    }

}