package br.com.maderal.payremind_me.person

import br.com.maderal.payremind_me.model.Person


interface OnDeleteListener {
    fun deleteItem(person: Person)
}