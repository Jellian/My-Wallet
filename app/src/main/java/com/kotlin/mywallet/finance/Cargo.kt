package com.kotlin.mywallet.finance

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.kotlin.mywallet.utils.Date

class Cargo(private var amount: Float = 0.0f, private var category: String = "", private var note: String = "", private var date: String = ""): Parcelable {

    private var type: String = ""
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
        type = parcel.readString()!!
    }

    init{
        type = if(amount<0) "Egreso"
        else "Ingreso"
    }

    fun getAmount(): Float{
        return this.amount
    }

    fun getType(): String{
        return this.type
    }

    fun getCategory(): String {
        return this.category
    }

    fun getNote(): String{
        return this.note
    }

    fun getDate(): String{
        return this.date
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(amount)
        parcel.writeString(category)
        parcel.writeString(note)
        parcel.writeString(date)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cargo> {
        override fun createFromParcel(parcel: Parcel): Cargo {
            return Cargo(parcel)
        }

        override fun newArray(size: Int): Array<Cargo?> {
            return arrayOfNulls(size)
        }
    }
}