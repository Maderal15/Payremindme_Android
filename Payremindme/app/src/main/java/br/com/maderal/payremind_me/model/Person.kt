package br.com.maderal.payremind_me.model

import android.os.Parcel
import android.os.Parcelable

class Person : Parcelable{

    var codigo: Long = 0
    var nome: String? = null
    var ativo: Boolean = false

    constructor() {
        this.nome = ""
        this.ativo = false
    }

    constructor(codigo: Long, nome: String, ativo: Boolean) {
        this.codigo = codigo
        this.nome = nome
        this.ativo = ativo
    }

    protected constructor(`in`: Parcel) {
        codigo = `in`.readLong()
        nome = `in`.readString()
        ativo = `in`.readInt() == 1

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(codigo)
        dest.writeString(nome)
        if(ativo){
            dest.writeInt(1)
        }else{
            dest.writeInt(0)
        }
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