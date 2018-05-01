package ramos.adriano.raul.ciclodevida

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("Create","Create")
    }

    override fun onStart() {
        super.onStart()
        Log.e("Start","Start")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("Restart","Restart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("Resume","Resume")
    }

    override fun onStop() {
        super.onStop()
        Log.e("Stop","Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("Destroy","Destroy")
    }
}
