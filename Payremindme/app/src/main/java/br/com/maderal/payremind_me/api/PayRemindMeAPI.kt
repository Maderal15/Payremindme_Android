package br.com.maderal.payremind_me.api

import br.com.maderal.payremind_me.model.PersonList
import br.com.maderal.payremind_me.model.TokenCredentials
import retrofit2.Call
import retrofit2.http.*

interface PayRemindMeAPI{

    @POST("/oauth/token")
    @FormUrlEncoded
    fun getAccessToken(@FieldMap params: Map<String,String>): Call<TokenCredentials>

    @POST("/oauth/token")
    @FormUrlEncoded
    fun refreshAccessToken(@FieldMap params: Map<String,String>): Call<TokenCredentials>

    @GET("/pessoas")
    fun getPessoas() : Call<PersonList>

}
