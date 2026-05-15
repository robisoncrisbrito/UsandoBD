package br.edu.utfpr.usandobd.adapter

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import br.edu.utfpr.usandobd.MainActivity
import br.edu.utfpr.usandobd.R
import br.edu.utfpr.usandobd.entity.Cadastro
import br.edu.utfpr.usandobd.database.DatabaseHandler.Companion.ID
import br.edu.utfpr.usandobd.database.DatabaseHandler.Companion.NOME
import br.edu.utfpr.usandobd.database.DatabaseHandler.Companion.TELEFONE

class ElementoListaAdapter(val context: Context, val registro: List<Cadastro>) : BaseAdapter() {

    override fun getCount(): Int {
        return registro.size
    }

    override fun getItem(pos: Int): Any? {

        val cadastro = Cadastro(
            registro.get( pos ).id,
            registro.get( pos ).nome,
            registro.get( pos ).telefone
        )

        return cadastro
    }

    override fun getItemId(pos: Int): Long {

        return registro.get(pos).id.toLong()

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

        tvNomeElementoLista.text = registro.get(pos).nome
        tvTelefoneElementoLista.text = registro.get(pos).telefone

        btEditarElementoLista.setOnClickListener {

            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra( "cod", registro.get(pos).id )
            intent.putExtra( "nome", registro.get(pos).nome )
            intent.putExtra( "telefone", registro.get(pos).telefone )
            context.startActivity(intent)
        }

        return v
    }

}