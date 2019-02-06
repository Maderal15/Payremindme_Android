package br.com.maderal.payremind_me.edit

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import br.com.maderal.payremind_me.R
import br.com.maderal.payremind_me.model.Person


class PersonViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    private val textViewFirstName = view.findViewById<TextView>(R.id.textViewFirstName)
    private val textViewLastName = view.findViewById<TextView>(R.id.textViewLastName)

    fun bindData(person: Person){
        textViewFirstName.text = person.codigo
        textViewLastName.text = person.nome
    }
}