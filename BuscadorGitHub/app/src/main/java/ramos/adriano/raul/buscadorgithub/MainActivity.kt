package ramos.adriano.raul.buscadorgithub

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    }
}
