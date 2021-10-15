package com.kotlin.mywallet.finance

import android.os.Parcel
import android.os.Parcelable

class Cuenta(private var id: Int, private var name: String, private val initialAmount: Float = 0.0f) : Parcelable{
    private var currency = "MXN"
    private var totalAmount = 0f
    private var expenses = mutableListOf<Cargo>()
    private var incomes = mutableListOf<Cargo>()

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
            parcel.readFloat()
    ) {
        currency = parcel.readString()!!
        totalAmount = parcel.readFloat()
        parcel.readTypedList(expenses, Cargo.CREATOR)
        parcel.readTypedList(incomes, Cargo.CREATOR)
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

    fun getCharges(): MutableList<Cargo>{
        val allCharges: MutableList<Cargo> = ArrayList()
//        this.incomes.forEach {
//            Log.d(it.getType(),"${it.getAmount()}MXN en ${it.getCategory()} con nota ${it.getNote()}")
//        }
        allCharges.addAll(this.incomes)
        allCharges.addAll(this.expenses)

        //allCharges.sortByDescending { it.getDate() }


        return allCharges
    }

    fun addExpense(charge: Cargo?){
        if (charge != null) {
            this.expenses.add(charge)
            this.totalAmount += charge.getAmount()
        }
    }

    fun addIncome(charge: Cargo?){
        if (charge != null) {
            this.incomes.add(charge)
            this.totalAmount += charge.getAmount()
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeFloat(initialAmount)
        parcel.writeString(currency)
        parcel.writeFloat(totalAmount)
        parcel.writeTypedList(expenses)
        parcel.writeTypedList(incomes)
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