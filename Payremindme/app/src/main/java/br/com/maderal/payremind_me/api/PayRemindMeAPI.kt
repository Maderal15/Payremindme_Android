package br.com.maderal.payremind_me.api

import br.com.maderal.payremind_me.model.Person
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

    @POST("/pessoas")
    fun addPerson(@Body person: Person): Call<Person>

    @PUT("/pessoas/{id}")
    fun updatePerson(@Path("id") id : Long ,@Body person: Person): Call<Person>

    @PUT("/pessoas/{id}/ativo")
    fun changePersonStatus(@Path("id") id : Long ,@Body ativo: Boolean): Call<Void>

    @DELETE("/pessoas/{id}")
    fun deletePerson(@Path("id") id : Long): Call<Void>

    @DELETE("/tokens/revoke")
    fun revokeToken(): Call<Void>
}
