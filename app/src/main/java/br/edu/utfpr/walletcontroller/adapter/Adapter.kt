package br.edu.utfpr.walletcontroller.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.view.ViewCompat
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
            cursor.getInt(cursor.getColumnIndexOrThrow("_id")),
            cursor.getString(cursor.getColumnIndexOrThrow("tipo")),
            cursor.getString(cursor.getColumnIndexOrThrow("detalhe")),
            cursor.getDouble(cursor.getColumnIndexOrThrow("valor")),
            cursor.getString(cursor.getColumnIndexOrThrow("datalancto"))
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

        val tvTipo = elementoLista.findViewById<TextView>(R.id.tvTipoElementoLista)
        val tvDetalhe = elementoLista.findViewById<TextView>(R.id.tvDetalheElementoLista)
        val tvValor = elementoLista.findViewById<TextView>(R.id.tvValorElementoLista)
        val tvDataLancto = elementoLista.findViewById<TextView>(R.id.tvDataLanctoElementoLista)

        cursor.moveToPosition(pos)

        tvTipo.text = cursor.getString(DataBaseHandler.TIPO)
        tvDetalhe.text = cursor.getString(DataBaseHandler.DETALHE)
        val valor = cursor.getDouble(DataBaseHandler.VALOR)
        tvValor.text = "R$ %.2f".format(valor)
        tvDataLancto.text = cursor.getString(DataBaseHandler.DATALANCTO)


        if(tvTipo.text == "Débito"){
            tvTipo.text = "D"
            tvTipo.setTextColor(Color.parseColor("#ff0000"))
        }else{
            tvTipo.text = "C"
            tvTipo.setTextColor(Color.parseColor("#00a100"))
        }

        elementoLista.setBackgroundColor(Color.parseColor("#CAD2ED"))

        return elementoLista
    }
}