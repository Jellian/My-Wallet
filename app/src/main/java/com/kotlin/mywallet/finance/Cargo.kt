package com.kotlin.mywallet.finance

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.kotlin.mywallet.utils.Date

class Cargo(private var amount: Float = 0.0f, private var category: String = "", private var note: String = ""): Parcelable {

    private var type: String = ""
    @RequiresApi(Build.VERSION_CODES.O)
    private var date: String = Date().now()

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
        type = parcel.readString()!!
        date = parcel.readString()!!
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(amount)
        parcel.writeString(category)
        parcel.writeString(note)
        parcel.writeString(type)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cargo> {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun createFromParcel(parcel: Parcel): Cargo {
            return Cargo(parcel)
        }

        override fun newArray(size: Int): Array<Cargo?> {
            return arrayOfNulls(size)
        }
    }
}