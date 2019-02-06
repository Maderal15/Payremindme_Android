package br.com.maderal.payremind_me.model

data class TokenCredentials(val access_token: String,
                            val token_type: String,
                            val expires_in: Long,
                            val scope: String,
                            val nome: String,
                            val jti: String)