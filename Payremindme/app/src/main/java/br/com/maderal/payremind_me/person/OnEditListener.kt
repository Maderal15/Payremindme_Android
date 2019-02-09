package br.com.maderal.payremind_me.person

import br.com.maderal.payremind_me.model.Person


interface OnEditListener {
    fun editItem(person: Person, index: Int)
    fun changeStatus(person: Person, index: Int)
}