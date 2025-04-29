package br.edu.utfpr.walletcontroller

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.utfpr.walletcontroller.adapter.Adapter
import br.edu.utfpr.walletcontroller.database.DataBaseHandler
import br.edu.utfpr.walletcontroller.databinding.ActivityListarBinding

class ListarActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListarBinding
    private lateinit var listView: ListView
    private lateinit var banco : DataBaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listView = findViewById(R.id.lvPrincipal)

        banco = DataBaseHandler(this)

        val header = layoutInflater.inflate(R.layout.header, listView, false)
        listView.addHeaderView(header) // <- Aqui adiciona o cabeçalho ANTES do adapter!

        val registros = banco.list()

        if (registros != null && registros.count > 0) {
            val adapter = Adapter(this, registros)
            binding.lvPrincipal.adapter = adapter
        } else {
            Toast.makeText(this, "Nenhum registro encontrado.", Toast.LENGTH_LONG).show()
        }
    }
}