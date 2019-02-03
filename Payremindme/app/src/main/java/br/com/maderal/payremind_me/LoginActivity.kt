package br.com.maderal.payremind_me

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import br.com.maderal.payremind_me.api.LoginAPI
import br.com.maderal.payremind_me.model.LoginCredentials
import br.com.maderal.payremind_me.model.TokenCredentials
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    fun logar(view: View) {

        val login = userName.text.toString()
        val senha = password.text.toString()


        val okhttp = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()

            request.addHeader("Content-Type", "application/x-www-form-urlencoded")

            if (!isUserLoggedIn()) {
                request.addHeader("Authorization", getToken())
            }
            chain.proceed(request.build())
        }.build()


        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-18-231-89-75.sa-east-1.compute.amazonaws.com:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttp)
            .build()

        val loginAPI = retrofit.create(LoginAPI::class.java)

        val params = HashMap<String, String>()
        params["username"] = "daniesouza@gmail.com"
        params["password"] = "admin"
        params["grant_type"] = "password"

        loginAPI.login(
            params
        ).enqueue(object : Callback<TokenCredentials> {
            override fun onFailure(call: Call<TokenCredentials>?, t: Throwable?) {

                t?.printStackTrace()
                Toast.makeText(
                    this@LoginActivity,
                    t?.message,
                    Toast.LENGTH_LONG)
                    .show()
            }

            override fun onResponse(call: Call<TokenCredentials>?, response: Response<TokenCredentials>?) {
                if(response?.isSuccessful == true) {
                    val body = response.body()
                    val headers = response.headers()

                    if (!headers.get("Set-Cookie").isNullOrEmpty()) {

                        val cookies = HashSet<String>()


                    }


                    val intent = Intent(this@LoginActivity, MenuActivity::class.java)

                    intent.putExtra("usuario", login)
                    startActivity(intent)
                    finish()

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

    private fun getToken(): String {
        return "Basic bW9iaWxlOm0wYjFsMzA="
    }

    private fun isUserLoggedIn(): Boolean {
        return false
    }
}
