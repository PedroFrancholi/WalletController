package br.edu.utfpr.walletcontroller

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.walletcontroller.database.DataBaseHandler
import br.edu.utfpr.walletcontroller.entity.Carteira
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var spTipo : Spinner
    private lateinit var spDetalhe : Spinner
    private lateinit var etValor : EditText
    private lateinit var dpDataLancto : DatePicker
    private lateinit var btLancar : Button
    private lateinit var btListar : Button
    private lateinit var btSaldo : Button

    private lateinit var banco : DataBaseHandler

    private lateinit var tipoSelected : String
    private lateinit var detalheSelected : String

    @SuppressLint("DefaultLocale")
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

        val tipos = listOf("Crédito", "Débito")
        val detalhes = listOf("Salário","Extra","Alimentação", "Transporte", "Saúde","Moradia")

        val adapterTipo = ArrayAdapter(this, android.R.layout.simple_list_item_1,  tipos)
        val adapterDetalhe = ArrayAdapter(this, android.R.layout.simple_list_item_1,  detalhes)

        spTipo.adapter = adapterTipo
        spDetalhe.adapter = adapterDetalhe

        spTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                tipoSelected = adapterView?.getItemAtPosition(pos).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        spDetalhe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                detalheSelected = adapterView?.getItemAtPosition(pos).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    fun btLancarOnClick(view: View) {
        val valorTexto = etValor.text.toString().trim()

        if (valorTexto.isEmpty()) {
            Toast.makeText(this, "Informe um valor antes de lançar!", Toast.LENGTH_LONG).show()
            return
        }

        val valor = etValor.text.toString().toDouble()

        val dia = dpDataLancto.dayOfMonth
        val mes = dpDataLancto.month + 1  // Sempre lembrar do +1 no mês!
        val ano = dpDataLancto.year

        val dataLancto = String.format("%02d-%02d-%04d", dia, mes, ano)

        val cadastro = Carteira(0, tipoSelected, detalheSelected ,valor,dataLancto)

        banco.insert(cadastro)

        Toast.makeText(this,"Registro Incluído...", Toast.LENGTH_LONG).show()

        etValor.setText("")
    }

    fun btListarOnClick(view: View) {
        val intent = Intent(this, ListarActivity::class.java)
        startActivity(intent)

    }

    fun btSaldoOnClick(view: View) {
        val saldo = banco.calcularSaldo()
        Toast.makeText(this, "Saldo atual: R$ %.2f".format(saldo), Toast.LENGTH_LONG).show()
    }
}