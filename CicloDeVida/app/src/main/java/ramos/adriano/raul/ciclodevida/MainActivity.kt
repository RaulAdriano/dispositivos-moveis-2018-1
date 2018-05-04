package ramos.adriano.raul.ciclodevida

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState!=null){
            if(savedInstanceState.containsKey("ConteudoSalvo")){
                val conteudoSalvo = savedInstanceState.getString("ConteudoSalvo")
                tv_mensagem_log.text = conteudoSalvo
            }
        }
        imprimir("Create")

    }

    override fun onStart() {
        super.onStart()
        imprimir("Start")
    }

    override fun onRestart() {
        super.onRestart()
        imprimir("Restart")
    }

    override fun onResume() {
        super.onResume()
        imprimir("Resume")
    }

    override fun onStop() {
        super.onStop()
        imprimir("Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        imprimir("Destroy")
    }

    fun imprimir(msg: String){
        Log.e("texto",msg)
        tv_mensagem_log.append("\n"+msg)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val conteudo =  tv_mensagem_log.text
        outState?.putString("ConteudoSalvo",conteudo.toString())
    }


}
