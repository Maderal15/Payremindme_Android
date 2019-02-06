package br.com.maderal.payremind_me.model

import android.os.Parcel
import android.os.Parcelable

class Person : Parcelable{

    var codigo: String? = null
    var nome: String? = null
    var ativo: Boolean = false

    constructor() {
        this.codigo = ""
        this.nome = ""
        this.ativo = false
    }

    constructor(codigo: String, nome: String, ativo: Boolean) {
        this.codigo = codigo
        this.nome = nome
        this.ativo = ativo
    }

    protected constructor(`in`: Parcel) {
        codigo = `in`.readString()
        nome = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(codigo)
        dest.writeString(nome)
    }

    companion object {

        @JvmField val CREATOR: Parcelable.Creator<Person> = object : Parcelable.Creator<Person> {
            override fun createFromParcel(`in`: Parcel): Person {
                return Person(`in`)
            }

            override fun newArray(size: Int): Array<Person?> {
                return arrayOfNulls(size)
            }
        }
    }
}