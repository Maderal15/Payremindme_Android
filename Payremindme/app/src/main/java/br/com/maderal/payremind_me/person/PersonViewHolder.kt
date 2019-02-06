package br.com.maderal.payremind_me.person

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Switch
import android.widget.TextView
import br.com.maderal.payremind_me.R
import br.com.maderal.payremind_me.model.Person


class PersonViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    private val textViewCodigo = view.findViewById<TextView>(R.id.textViewCodigo)
    private val textViewNome = view.findViewById<TextView>(R.id.textViewNome)
    private val switchAtivo = view.findViewById<Switch>(R.id.switchViewAtivo)

    fun bindData(person: Person){
        textViewCodigo.text = person.codigo.toString()
        textViewNome.text = person.nome
        switchAtivo.isChecked = person.ativo
    }
}