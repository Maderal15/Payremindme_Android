package br.com.maderal.payremind_me

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }



    fun openPessoas(view: View) {
        val intent = Intent(this, ListaPessoasActivity::class.java)

        startActivity(intent)
    }
}
