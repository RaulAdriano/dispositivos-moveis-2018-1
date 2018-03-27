package ramos.adriano.raul.buscadorgithub

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        condicao()
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


