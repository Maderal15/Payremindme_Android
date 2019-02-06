package br.com.maderal.payremind_me.api

import android.content.SharedPreferences
import br.com.maderal.payremind_me.model.PersonList
import br.com.maderal.payremind_me.model.TokenCredentials
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class PayRemindMeService(sharedPreferences: SharedPreferences?){

    private var prefs: SharedPreferences? = null;
    var API_URL = "http://ec2-18-231-89-75.sa-east-1.compute.amazonaws.com:8080";

    init {
        prefs = sharedPreferences
    }


    fun getAccessToken(username: String, password: String): Call<TokenCredentials>{

        val okhttp = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()

            request.addHeader("Content-Type", "application/x-www-form-urlencoded")
            request.addHeader("Authorization", "Basic bW9iaWxlOm0wYjFsMzA=")
            chain.proceed(request.build())
        }.cookieJar(SessionCookieJar(prefs)).build()


        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttp)
            .build()

        val payRemindMeAPI = retrofit.create(PayRemindMeAPI::class.java)

        val params = HashMap<String, String>()
        params["username"] = "daniesouza@gmail.com"
        params["password"] = "admin"
        params["grant_type"] = "password"

        return payRemindMeAPI.getAccessToken(params);
    }

    // refresh token usando o cookie com refresh_token oculto
    fun refreshAccessToken(): Call<TokenCredentials>{

        val okhttp = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()

            request.addHeader("Content-Type", "application/x-www-form-urlencoded")
            request.addHeader("Authorization", "Basic bW9iaWxlOm0wYjFsMzA=")
            chain.proceed(request.build())
        }.cookieJar(SessionCookieJar(prefs)).build()


        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttp)
            .build()

        val payRemindMeAPI = retrofit.create(PayRemindMeAPI::class.java)

        val params = HashMap<String, String>()
        params["grant_type"] = "refresh_token"

        return payRemindMeAPI.refreshAccessToken(params);
    }


    // refresh token usando o cookie com refresh_token oculto
    fun getPessoas(): Call<PersonList>{

        val json = prefs?.getString("tokenCredentials",null)
        val tokenCredentials = Gson().fromJson(json,TokenCredentials::class.java)

        val okhttp = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()

            request.addHeader("Content-Type", "application/json")
            request.addHeader("Authorization", "Bearer "+tokenCredentials.access_token )
            chain.proceed(request.build())
        }.build()


        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttp)
            .build()

        val payRemindMeAPI = retrofit.create(PayRemindMeAPI::class.java)

        val params = HashMap<String, String>()
        params["grant_type"] = "refresh_token"

        return payRemindMeAPI.getPessoas();
    }
}