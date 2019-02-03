package br.com.maderal.payremind_me.model

data class LoginCredentials(private val username: String, private val password: String, private val grant_type: String)