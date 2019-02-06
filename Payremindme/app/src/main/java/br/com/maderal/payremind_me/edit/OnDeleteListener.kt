package br.com.maderal.payremind_me.edit

import br.com.maderal.payremind_me.model.Person


interface OnDeleteListener {
    fun deleteItem(person: Person)
}