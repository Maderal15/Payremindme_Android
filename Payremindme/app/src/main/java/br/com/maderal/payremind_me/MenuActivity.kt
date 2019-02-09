package br.com.maderal.payremind_me

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.maderal.payremind_me.api.PayRemindMeAPIService
import br.com.maderal.payremind_me.person.ListaPessoasActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity() {

    private var payRemindMeAPIService : PayRemindMeAPIService? = null
    private var PREFS_FILENAME = "payremind.prefs"
    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        this.payRemindMeAPIService = PayRemindMeAPIService(prefs)
    }


    fun openPessoas(view: View) {
        val intent = Intent(this, ListaPessoasActivity::class.java)
        startActivity(intent)
    }

    fun openSobre(view: View) {
        val intent = Intent(this, SobreActivity::class.java)
        startActivity(intent)
    }

    fun logout(view: View){

        this.payRemindMeAPIService?.logout()?.enqueue(object :
            Callback<Void> {
            override fun onFailure(call: Call<Void>?, t: Throwable?) {

                t?.printStackTrace()
                Toast.makeText(
                    this@MenuActivity,
                    t?.message,
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                prefs?.edit()?.putString("tokenCredentials",null)?.apply()
                val intent = Intent(this@MenuActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

    }
}
