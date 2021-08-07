package com.kotlin.mywallet.finance

import android.os.Parcel
import android.os.Parcelable

class Cuenta(private var id: Int, private var name: String, private val initialAmount:Float = 0.0f) : Parcelable{
    private var currency = "MXN"
    private var totalAmount = 0f
    private var expenses = mutableListOf<Egreso>()
    private var incomes = mutableListOf<Ingreso>()

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readFloat()
    ) {
        currency = parcel.readString()!!
        totalAmount = parcel.readFloat()
    }

    init{
        totalAmount = initialAmount
    }

    fun getName(): String{
        return name
    }

    fun getTotalAmount(): Float{
        return totalAmount
    }

    fun addExpense(charge: Egreso?){
        if (charge != null) {
            expenses.add(charge)
            this.totalAmount += charge.getAmount()
        }
    }

    fun addIncome(charge: Ingreso?){
        if (charge != null) {
            incomes.add(charge)
            this.totalAmount += charge.getAmount()
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeFloat(initialAmount)
        parcel.writeString(currency)
        parcel.writeFloat(totalAmount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cuenta> {
        override fun createFromParcel(parcel: Parcel): Cuenta {
            return Cuenta(parcel)
        }

        override fun newArray(size: Int): Array<Cuenta?> {
            return arrayOfNulls(size)
        }
    }


}