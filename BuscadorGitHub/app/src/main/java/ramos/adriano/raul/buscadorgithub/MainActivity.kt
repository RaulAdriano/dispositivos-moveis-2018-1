package ramos.adriano.raul.buscadorgithub

import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity(),
        SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val corFundo = sharedPreferences.getString(
                getString(R.string.pref_cor_fundo),
                getString(R.string.pref_cor_fundo_padrao)
        )
        window.decorView.setBackgroundColor(selecionaCorDeFundo(corFundo))

                val exibirUrl = sharedPreferences.getBoolean(getString(R.string.exibir_url), resources.getBoolean(R.bool.pref_exibir_url_padrao))

                if (!exibirUrl) {
                    tv_url.visibility = View.INVISIBLE
                }


        condicao()


    }

   override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == getString(R.string.exibir_url)) {
            val exibirUrl = sharedPreferences.getBoolean(
                    key,
                    resources.getBoolean(R.bool.pref_exibir_url_padrao)
            )
            tv_url.visibility = if (exibirUrl) View.VISIBLE else View.INVISIBLE
        } else if (key == getString(R.string.pref_cor_fundo)) {
            val corFundo = sharedPreferences.getString(
                    key,
                    getString(R.string.pref_cor_fundo_padrao)
            )
            window.decorView.setBackgroundColor(selecionaCorDeFundo(corFundo))
        }
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_buscar){
            //Toast.makeText(this,"Clicou",Toast.LENGTH_LONG).show();
            buscarGitHub();
            return true;
        }

        if (item?.itemId == R.id.action_configuracoes) {
                    val intent = Intent(this, ConfiguracaoActivity::class.java)
                    startActivity(intent)
                }
        return super.onOptionsItemSelected(item);
    }

    fun buscarGitHub(){
        val busca = et_busca.text.toString();
        val url = NetworkUtils.construirUrl(busca)
        tv_url.text = url.toString();

        if(url != null){
            val task = GitHubBuscaTask()
            task.execute(url)
        }

    }

    fun selecionaCorDeFundo(corFundo: String): Int {

        return when (corFundo) {

            getString(R.string.pref_cor_fundo_branco_valor) -> ContextCompat.getColor(this, R.color.fundoBranco)

            getString(R.string.pref_cor_fundo_verde_valor) -> ContextCompat.getColor(this, R.color.fundoVerde)

            getString(R.string.pref_cor_fundo_azul_valor) -> ContextCompat.getColor(this, R.color.fundoAzul)

            else -> ContextCompat.getColor(this, R.color.fundoBranco)

        }

    }

    fun exibirResultado(){
        tv_github_resultado.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirMensagemErro(){
        tv_github_resultado.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.VISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirProgressBar(){
        tv_github_resultado.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.VISIBLE
    }


    fun condicao(){
        val dadosJson = """ {
                                "temperatura":{
                                    "minima":11.34,
                                    "maxima":19.01
                                },

                                "clima":{
                                    "id":801,
                                    "condicoes":"nuvens",
                                    "descricao":"poucas nuvens"
                                },


                                "pressao":1023.51,

                                "umidade":87

                            }"""
        val objetoJson = JSONObject(dadosJson)
        val clima = objetoJson.getJSONObject("clima");
        val condicao = clima.getString("condicoes");

        Log.d("json", "$condicao");

    }


    inner class GitHubBuscaTask : AsyncTask<URL, Void, String>() {

        override fun doInBackground(vararg params: URL?): String? {
            try {
                val url = params[0]
                val resultado = NetworkUtils.obterRespostaDaUrlHttp(url!!)
                return resultado
            }catch (ex :  Exception){
                ex.printStackTrace()
            }
            return null;
        }

        override fun onPostExecute(result: String?) {
           if (result != null){
                tv_github_resultado.text = result;
                exibirResultado()
           }else {
               exibirMensagemErro()
           }


        }

        override fun onPreExecute() {
            exibirProgressBar()
        }



    }
}


