package br.com.maderal.payremind_me.api

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import br.com.maderal.payremind_me.model.TokenCredentials
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.*


class SessionCookieJar(sharedPreferences: SharedPreferences?) : CookieJar {

    private var PREFS_FILENAME = "payremind.prefs"
    private var cookies: List<Cookie>? = null
    private var prefs: SharedPreferences? = null;

    init {
        prefs = sharedPreferences
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (url.encodedPath().endsWith("/oauth/token")) {
            this.cookies = ArrayList(cookies)
            prefs?.edit()?.putString("cookiesRefreshToken", Gson().toJson(this.cookies))?.apply()
        }
    }


    override fun loadForRequest(url: HttpUrl): List<Cookie> {

        if (url.encodedPath().endsWith("/oauth/token")) {

            if(cookies == null){
                val jsonCookieRefreshToken = prefs?.getString("cookiesRefreshToken",null)
                val cookieType = object : TypeToken<List<Cookie>>() {}.type
                cookies = Gson().fromJson<List<Cookie>>(jsonCookieRefreshToken, cookieType)
            }

            if(cookies == null){
                return Collections.emptyList()
            }

            return cookies as List<Cookie>
        } else{
            return Collections.emptyList()
        }
    }


}