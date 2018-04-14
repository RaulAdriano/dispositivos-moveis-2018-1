package ramos.adriano.raul.metaspessoais

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    //dados das metas
    private var dados = mutableListOf<Metas>()

    //posicoes complementares
    private var posicao = -1
    private var pos = -1

    //variaveis da meta
    var titulo: String = ""
    var descricao: String = ""
    var dataInicial: String = ""
    var dataFinal: String = ""
    var situacao: String = "Ativo"

    //lista de metas
    var listV: ListView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listV = findViewById(R.id.lista_metas)

        listV?.setOnItemClickListener { parent, view, position, id ->
            pos = position
            situacaoMeta()
        }

        listV?.setOnCreateContextMenuListener(View.OnCreateContextMenuListener { contextMenu, view, contextMenuInfo -> menuInflater.inflate(R.menu.menu_changes, contextMenu) })
    }


    //item do menu inserir meta
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.acao_inserir) {
            inserir()
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_insert, menu)
        return true
    }

    fun inserir() {
        getCampos()
        if (dataInicial == "") {
            Toast.makeText(this, "Informe uma data", Toast.LENGTH_LONG).show()
        } else if (titulo == "") {
            Toast.makeText(this, "Informe um titulo", Toast.LENGTH_LONG).show()
        } else if (descricao == "") {
            Toast.makeText(this, "Informe uma Descrição ", Toast.LENGTH_LONG).show()
        } else if (dataInicial != "" && titulo != "" && descricao != "") {

            val meta = Metas(dataInicial, dataFinal,titulo, descricao, situacao)

            var verificarData = validarData(dataInicial)

            if (verificarData != null) {
                if (posicao == -1) {
                    dados.add(meta)
                } else {
                    dados[posicao] = meta
                }
            }
            atualizarCampos()
        }
    }

    //atualiza os campos com os novos dados
    fun atualizarCampos() {
        val adaptar = ArrayAdapter<Metas>(this, android.R.layout.simple_list_item_1, dados)
        listV?.adapter = adaptar
        adaptar.notifyDataSetChanged()
        posicao = -1
        limparCampos()
    }

    //pega os campos
    fun getCampos() {
        dataInicial = cad_data.text.toString()
        titulo = cad_titulo.text.toString()
        descricao = cad_descricao.text.toString()
        cad_data.requestFocus()
    }


    //finaliza a meta
    fun finalizar() {
        val data = pegarData()
        dataFinal = pegarData().toString()
        situacao = "Meta Finalizada"
        retornarSelecionado()
        inserir()
        atualizarCampos()
        Toast.makeText(this, "Meta Concluida", Toast.LENGTH_LONG).show()
    }

    //pega a data
    fun pegarData(): CharSequence {
        val data = Date()
        val dataFormatada = android.text.format.DateFormat.format("dd/MM/yyyy ", data)
        return dataFormatada
    }

    //limpa os campos
    fun limparCampos() {
        cad_data.setText("")
        cad_titulo.setText("")
        cad_descricao.setText("")
        situacao = "Meta Ativa"
        dataFinal = ""
    }




    private fun retornarSelecionado() {
        val atual = dados[posicao]
        cad_data.setText(atual.dataInicial)
        cad_titulo.setText(atual.titulo)
        cad_descricao.setText(atual.descricao)
    }

    fun vericarData(data: String): Boolean {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        var dt = Date()
        dt = sdf.parse(data)
        val dataAgora = Calendar.getInstance().time
        var tpDia = (24 * 60 * 60 * 1000)
        val difDia = ((dt.time - dataAgora.time) / tpDia)

        return difDia >= 0
    }

    fun excluir() {
        dados.removeAt(posicao)
        limparCampos()
        atualizarCampos()
    }



    //calcula o tempo da data
    fun situacaoMeta() {
        val atual = dados[pos]
        var sit = atual.situacao
        var tpDia = (24 * 60 * 60 * 1000)
        var dt = validarData(atual.dataInicial)
        val dataAgora = Calendar.getInstance().time
        var difDia = (((dt!!.time - dataAgora.time) / tpDia) + 1)
        pos = -1

        if (sit != "F") {
            if (difDia < 0) {
                Toast.makeText(this, "A meta nao foi cumprida", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "faltam (" + difDia + ") Dia(s) para o Encerramento da meta!!!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Meta Finalizada!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {

        val menuList = item?.menuInfo as AdapterView.AdapterContextMenuInfo
        posicao = menuList.position

        if (item.itemId == R.id.acao_editar) {
            retornarSelecionado()
        } else if (item.itemId == R.id.acao_excluir) {
            excluir()
        } else if (item.itemId == R.id.acao_finalizar) {
            finalizar()
        }
        return super.onContextItemSelected(item)
    }


    //valida a data imformada
    fun validarData(data: String): Date? {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        sdf.isLenient = false
        var dataAgora: Date = Date()
        var dt = dataAgora

        try {
            dataAgora = sdf.parse(data)
            if (vericarData(data)) {
                return dataAgora
            } else {
                Toast.makeText(this, "Data informada de forma invalida", Toast.LENGTH_SHORT).show()
                return null
            }
        } catch (ex: Exception) {
            Toast.makeText(this, "hey, isto não é uma data !", Toast.LENGTH_SHORT).show()
            return null
        }
    }
}
