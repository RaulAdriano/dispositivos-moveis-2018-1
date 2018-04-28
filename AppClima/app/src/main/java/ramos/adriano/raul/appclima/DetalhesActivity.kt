package ramos.adriano.raul.appclima

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detalhes.*

class DetalhesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)
        val msg = intent.getStringExtra("prev")
        Toast.makeText(this, "Previsão: $msg", Toast.LENGTH_SHORT).show()
        tv_exibir_previsao.text = msg
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId==R.id.acao_compartilhar){
            val texto = intent.getStringExtra("prev")
            val intentCompartilhar = ShareCompat.IntentBuilder
                    .from(this)
                    .setType("text/plain")
                    .setChooserTitle("Compartilhando")
                    .setText(texto)
                    .intent

            if (intentCompartilhar.resolveActivity(packageManager)!=null)
                startActivity(intentCompartilhar)
        }
        return  super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detalhes,menu)
        return true
    }
}
