package br.com.maderal.payremind_me.api

import br.com.maderal.payremind_me.model.LoginCredentials
import br.com.maderal.payremind_me.model.TokenCredentials
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginAPI{

    @POST("/oauth/token")
    @FormUrlEncoded
    fun login(@FieldMap params: Map<String,String>): Call<TokenCredentials>
}
