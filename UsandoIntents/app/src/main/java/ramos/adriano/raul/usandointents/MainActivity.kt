package ramos.adriano.raul.usandointents

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.view.View
import android.widget.ShareActionProvider
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_enviar.setOnClickListener({
            val mensagem = et_mensagem.text.toString()
           // Toast.makeText(this,mensagem,Toast.LENGTH_LONG).show()

            var activityDestino = SegundaActivity::class.java

            var intent = Intent(this,activityDestino)
            intent.putExtra("valor",mensagem)
            startActivity(intent)

        })


        //abrir site
        btn_abrir_site.setOnClickListener({
            val acao = Intent.ACTION_VIEW
            val dado = Uri.parse("http://www.grupointegrado.br")
            val intentImplicito = Intent(acao,dado)
            if (intentImplicito.resolveActivity(packageManager)!= null){
                startActivity(intentImplicito)
            }
        })

        //abrir endereco no mapa
        btn_abrir_mapa.setOnClickListener({
            val endereco = "Rua União da Vitória, 703, Peabiru - PR"
            val buider = Uri.Builder()
                    .scheme("geo")
                    .path("0,0")
                    .appendQueryParameter("q",endereco)
            val uriEndereco = buider.build()
            val intent = Intent(Intent.ACTION_VIEW, uriEndereco)
            if (intent.resolveActivity(packageManager)!=null)
                startActivity(intent)
        })

        //compartilhar  texto
        btn_compartilhar.setOnClickListener({
            val texto = et_mensagem.text.toString()
            val intentCompartilhar = ShareCompat.IntentBuilder
                    .from(this)
                    .setType("text/plain")
                    .setChooserTitle("Compartilhando")
                    .setText(texto)
                    .intent

            if (intentCompartilhar.resolveActivity(packageManager)!=null)
                startActivity(intentCompartilhar)
        })
    }

//    fun exibirMensagem( view : View){
//        Toast.makeText(this,et_mensagem.text,Toast.LENGTH_LONG).show()
//    }
}
