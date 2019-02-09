package br.com.maderal.payremind_me.model

import android.os.Parcel
import android.os.Parcelable

class State : Parcelable{

    var codigo: Long = 26
    var nome: String = "São Paulo"

    constructor() {
    }

    constructor(codigo: Long, nome: String, ativo: Boolean) {
        this.codigo = codigo
        this.nome = nome
    }

    protected constructor(`in`: Parcel) {
        codigo = `in`.readLong()
        nome = `in`.readString()

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(codigo)
        dest.writeString(nome)
    }

    companion object {

        @JvmField val CREATOR: Parcelable.Creator<State> = object : Parcelable.Creator<State> {
            override fun createFromParcel(`in`: Parcel): State {
                return State(`in`)
            }

            override fun newArray(size: Int): Array<State?> {
                return arrayOfNulls(size)
            }
        }
    }
}