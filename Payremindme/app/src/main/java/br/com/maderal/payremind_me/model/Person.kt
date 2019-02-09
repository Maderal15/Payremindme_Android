package br.com.maderal.payremind_me.model

import android.os.Parcel
import android.os.Parcelable

class Person : Parcelable{

    var codigo: Long = 0
    var nome: String = ""
    var ativo: Boolean = false
    var endereco: Address = Address()
    var contatos: List<Contact> = ArrayList<Contact>()


    constructor() {
    }

    constructor(codigo: Long, nome: String, ativo: Boolean,endereco: Address,contatos: List<Contact>) {
        this.codigo = codigo
        this.nome = nome
        this.ativo = ativo
        this.endereco = endereco
        this.contatos = contatos
    }

    constructor(parcel: Parcel) : this() {
        codigo = parcel.readLong()
        nome = parcel.readString()
        ativo = parcel.readByte() != 0.toByte()
        endereco = parcel.readParcelable(Address::class.java.classLoader)
        contatos = parcel.createTypedArrayList(Contact.CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(codigo)
        parcel.writeString(nome)
        parcel.writeByte(if (ativo) 1 else 0)
        parcel.writeParcelable(endereco, flags)
        parcel.writeTypedList(contatos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }

}