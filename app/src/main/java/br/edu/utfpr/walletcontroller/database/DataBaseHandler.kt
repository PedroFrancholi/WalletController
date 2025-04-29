package br.edu.utfpr.walletcontroller.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.utfpr.walletcontroller.entity.Carteira

class DataBaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(banco: SQLiteDatabase?) {
        banco?.execSQL("CREATE TABLE IF NOT EXISTS ${TABLE_NAME} " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, tipo TEXT, detalhe TEXT, valor FLOAT, datalancto TEXT)")
    }

    override fun onUpgrade(banco: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        banco?.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME}")
        onCreate(banco)    }

    fun insert(carteira: Carteira){
        val banco = this.writableDatabase

        val registro = ContentValues()
        registro.put("tipo", carteira.tipo)
        registro.put("detalhe", carteira.detalhe)
        registro.put("valor", carteira.valor)
        registro.put("datalancto", carteira.datalancto)

        banco.insert("carteira",null,registro)
    }

    fun list() : Cursor {
        val banco = this.readableDatabase

        return banco.rawQuery("SELECT * FROM carteira ORDER BY datalancto, valor", null)

    }

    fun calcularSaldo():Double{
        val banco = this.writableDatabase
        var saldo = 0.0

        val cursor = banco.rawQuery("SELECT tipo, valor FROM carteira", null)

        if(cursor.moveToNext()){
            do {
                val tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
                val valor = cursor.getString(cursor.getColumnIndexOrThrow("valor"))

                if(tipo == "Crédito"){
                    saldo = saldo.toDouble() + valor.toDouble()
                }else{
                    saldo = saldo.toDouble() - valor.toDouble()
                }
            }while (cursor.moveToNext())
        }

        return saldo
    }

    companion object{
        public const val DATABASE_VERSION = 1
        public const val DATABASE_NAME = "dbfile.sqlite"
        public const val TABLE_NAME = "carteira"
        public const val ID = 0
        public const val TIPO = 1
        public const val DETALHE = 2
        public const val VALOR = 3
        public const val DATALANCTO = 4
    }
}
