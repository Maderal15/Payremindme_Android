package br.com.maderal.payremind_me.edit

import br.com.maderal.payremind_me.model.Person


interface OnEditListener {
    fun editItem(person: Person, index: Int)
}