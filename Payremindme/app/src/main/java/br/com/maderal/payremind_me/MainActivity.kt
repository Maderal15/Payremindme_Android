package br.com.maderal.payremind_me

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import br.com.maderal.payremind_me.model.TokenCredentials
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    private val SPLASH_TIME = 3000;

    private var PREFS_FILENAME = "payremind.prefs"
    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)

        Handler().postDelayed({

            val json = prefs?.getString("tokenCredentials",null)
            val tokenCredentials = Gson().fromJson(json,TokenCredentials::class.java)

            if(tokenCredentials == null){
                val mySuperIntent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(mySuperIntent)
            }else{
                val mySuperIntent = Intent(this@MainActivity, MenuActivity::class.java)
                startActivity(mySuperIntent)
            }
            finish()
        }, SPLASH_TIME.toLong())

     //   carrega()
    }

    /*private fun carrega() {
        Handler().postDelayed({
            proximaTela()
        }, 3000L)
    }

    private fun proximaTela() {
        val proximaTelaIntent = Intent(this,
            LoginActivity::class.java)
        startActivity(proximaTelaIntent)
        finish()
    }*/
}
