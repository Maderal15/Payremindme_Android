package br.com.maderal.payremind_me.model

import android.os.Parcel
import android.os.Parcelable

class City : Parcelable{

    var codigo: Long = 4
    var nome: String = "São Paulo"
    var estado: State = State()

    constructor() {
    }

    constructor(codigo: Long, nome: String) {
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

        @JvmField val CREATOR: Parcelable.Creator<City> = object : Parcelable.Creator<City> {
            override fun createFromParcel(`in`: Parcel): City {
                return City(`in`)
            }

            override fun newArray(size: Int): Array<City?> {
                return arrayOfNulls(size)
            }
        }
    }
}