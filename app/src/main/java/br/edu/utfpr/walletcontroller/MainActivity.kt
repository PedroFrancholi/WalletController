package br.edu.utfpr.walletcontroller

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.walletcontroller.database.DataBaseHandler
import br.edu.utfpr.walletcontroller.entity.Carteira

class MainActivity : AppCompatActivity() {

    private lateinit var spTipo : Spinner
    private lateinit var spDetalhe : Spinner
    private lateinit var etValor : EditText
    private lateinit var dpDataLancto : DatePicker
    private lateinit var btLancar : Button
    private lateinit var btListar : Button
    private lateinit var btSaldo : Button

    private lateinit var banco : DataBaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spTipo = findViewById(R.id.spTipo)
        spDetalhe = findViewById(R.id.spDetalhe)
        etValor = findViewById(R.id.etValor)
        dpDataLancto = findViewById(R.id.dpDataLancto)
        btLancar = findViewById(R.id.btLancar)
        btListar = findViewById(R.id.btListar)
        btSaldo = findViewById(R.id.btSaldo)

        banco = DataBaseHandler(this)

        val tipos = listOf("Débito", "Crédito")
        val detalhes = listOf("Salário","Extra","Alimentação", "Transporte", "Saúde","Moradia")

        val adapterTipo = ArrayAdapter(this, android.R.layout.simple_list_item_1,  tipos)
        val adapterDetalhe = ArrayAdapter(this, android.R.layout.simple_list_item_1,  detalhes)

    }

    fun btLancarOnClick(view: View) {
        val cadastro = Carteira(0, spTipo.toString(), spDetalhe.toString(),etValor.toString().toDouble(),dpDataLancto.toString())

        banco.insert(cadastro)

        Toast.makeText(this,"Registro Incluído...", Toast.LENGTH_LONG).show()
    }

    fun btListarOnClick(view: View) {
        val intent = Intent(this, ListarActivity::class.java)

        startActivity(intent)
    }

    fun btSaldoOnClick(view: View) {}
}