package br.com.maderal.payremind_me

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import br.com.maderal.payremind_me.api.PayRemindMeAPIService
import br.com.maderal.payremind_me.model.TokenCredentials
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    private var payRemindMeAPIService : PayRemindMeAPIService? = null
    private var PREFS_FILENAME = "payremind.prefs"
    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        this.payRemindMeAPIService = PayRemindMeAPIService(prefs)
    }

    fun logar() {

        val username = userName.text.toString()
        val password = password.text.toString()


        this.payRemindMeAPIService?.login(username,password)?.enqueue(object : Callback<TokenCredentials> {
            override fun onFailure(call: Call<TokenCredentials>?, t: Throwable?) {

                t?.printStackTrace()
                Toast.makeText(
                    this@LoginActivity,
                    t?.message,
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(call: Call<TokenCredentials>?, response: Response<TokenCredentials>?) {
                if (response?.isSuccessful ?: false) {
                    val tokenCredentials = response?.body()
                    val intent = Intent(this@LoginActivity, MenuActivity::class.java)

                    prefs?.edit()?.putString("tokenCredentials",Gson().toJson(tokenCredentials))?.apply()

                    intent.putExtra("usuario", username)
                    startActivity(intent)

                } else {

                    Toast.makeText(
                        this@LoginActivity,
                        "Usuário ou senha inválidos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}
