package br.com.maderal.payremind_me.model

data class TokenCredentials(private val access_token: String,
                            private val token_type: String,
                            private val expires_in: Long,
                            private val scope: String,
                            private val nome: String,
                            private val jti: String)