package br.com.maderal.payremind_me.model

import android.os.Parcel
import android.os.Parcelable

class Address : Parcelable{

    var logradouro: String = ""
    var numero: Long = 0
    var complemento: String? = ""
    var bairro: String = ""
    var cep: String = ""
    var cidade: City = City()

    constructor() {
    }

    constructor(parcel: Parcel) : this() {
        logradouro = parcel.readString()
        numero = parcel.readLong()
        complemento = parcel.readString()
        bairro = parcel.readString()
        cep = parcel.readString()
        cidade = parcel.readParcelable(City::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(logradouro)
        parcel.writeLong(numero)
        parcel.writeString(complemento)
        parcel.writeString(bairro)
        parcel.writeString(cep)
        parcel.writeParcelable(cidade, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Address> {
        override fun createFromParcel(parcel: Parcel): Address {
            return Address(parcel)
        }

        override fun newArray(size: Int): Array<Address?> {
            return arrayOfNulls(size)
        }
    }

}