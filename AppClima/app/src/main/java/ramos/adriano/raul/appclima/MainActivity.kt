package ramos.adriano.raul.appclima

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class MainActivity : AppCompatActivity() {

    var previsaoAdapter : PrevisaoAdapter? = null
    val context : Context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        previsaoAdapter = PrevisaoAdapter(null)

        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        rv_clima.layoutManager = layoutManager
        rv_clima.adapter= previsaoAdapter

        carregarDadosClima()

    }

    fun exibirResultado(){
        rv_clima.visibility = View.VISIBLE
        tv_msgErro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirMSG(){
        rv_clima.visibility = View.INVISIBLE
        tv_msgErro.visibility = View.VISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirPB(){
        rv_clima.visibility = View.INVISIBLE
        tv_msgErro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId==R.id.acao_atualizar){
            carregarDadosClima()
            return true
        }
        return  super.onOptionsItemSelected(item)
    }

    fun carregarDadosClima(){
        val local = ClimaPreferencias.getLocalizacaoSalva(this)
        val url = NetworkUtils.construirUrl(local)
        buscarClimaTask().execute(url)
    }

    inner class buscarClimaTask() : AsyncTask<URL, Void, String>() {

        override fun onPreExecute() {
            exibirPB()
        }

        override fun doInBackground(vararg p0: URL?): String? {
            try {
                val url = p0[0]
                val result = NetworkUtils.obterRespostaDaUrlHttp(url!!)

                return result
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            if (result != null) {
                val retorno = JsonUtils.getSimplesStringsDeClimaDoJson(context, result)
                previsaoAdapter?.setDadosClima(retorno)
                exibirResultado()
            } else {
                exibirMSG()
            }
        }
    }

}
