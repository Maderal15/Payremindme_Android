package br.com.maderal.payremind_me.edit

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.maderal.payremind_me.R
import br.com.maderal.payremind_me.model.Person


class PersonAdapter(val listPerson: MutableList<Person>, val deleteListener: OnDeleteListener,
                    val editListener: OnEditListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return listPerson.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val personViewHolder = holder as PersonViewHolder
        personViewHolder.bindData(listPerson[position])
        personViewHolder.view.setOnLongClickListener { l ->
            deleteListener.deleteItem(listPerson[position])
            true
        }
        personViewHolder.view.setOnClickListener {
            editListener.editItem(listPerson[position], position)
        }
    }
}