package br.edu.utfpr.walletcontroller.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import br.edu.utfpr.walletcontroller.R
import br.edu.utfpr.walletcontroller.database.DataBaseHandler
import br.edu.utfpr.walletcontroller.entity.Carteira

class Adapter(var context:Context, var cursor: Cursor) : BaseAdapter(){
    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(pos: Int): Any {
        cursor.moveToPosition(pos)

        val carteira = Carteira(
            cursor.getInt(DataBaseHandler.ID),
            cursor.getString(DataBaseHandler.TIPO),
            cursor.getString(DataBaseHandler.DETALHE),
            cursor.getDouble(DataBaseHandler.VALOR),
            cursor.getString(DataBaseHandler.DATALANCTO)
        )
        return carteira
    }

    override fun getItemId(pos: Int): Long {
        cursor.moveToPosition(pos)
        return cursor.getInt(DataBaseHandler.ID).toLong()
    }

    @SuppressLint("MissingInflatedId")
    override fun getView(pos: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val elementoLista = inflater.inflate(R.layout.elemento_lista, null)

        val spTipo = elementoLista.findViewById<Spinner>(R.id.spTipo)
        val spDetalhe = elementoLista.findViewById<Spinner>(R.id.spDetalhe)
        val etValor = elementoLista.findViewById<EditText>(R.id.etValor)
        val dpDataLancto = elementoLista.findViewById<DatePicker>(R.id.dpDataLancto)
        val btLancar = elementoLista.findViewById<Button>(R.id.btLancar)
        val btListar = elementoLista.findViewById<Button>(R.id.btListar)
        val btSaldo= elementoLista.findViewById<Button>(R.id.btSaldo)

//        if(etValor){
//
//        }

        return elementoLista
    }
}