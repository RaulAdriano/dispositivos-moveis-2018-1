package ramos.adriano.raul.provaprimeirobimestre

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
        menuInflater.inflate(R.menu.menu_calcular, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.calcular) {
            calcular()
        }
        return super.onOptionsItemSelected(item)
    }

    fun calcular(){

        val base  = ed_base.text.toString()
        val altura = ed_altura.text.toString()
        val profundidade = ed_profundidade.text.toString()

        if (base.isEmpty()){
            msgErro()
        }else if (altura.isEmpty()){
            msgErro()
        }else if (profundidade.isEmpty()){
            msgErro()
        }else {

            //calcular area
            var area = ( base.toFloat() * altura.toFloat())
            tv_area.text = "AREA : " + area


            //calcular volume
            val volume = base.toFloat() * altura.toFloat() * profundidade.toFloat()
            tv_volume.text = "VOLUME : " + volume

        }



    }

    fun msgErro(){
        Toast.makeText(this," Um ou mais campos estao vazios",Toast.LENGTH_LONG).show()
    }
}
