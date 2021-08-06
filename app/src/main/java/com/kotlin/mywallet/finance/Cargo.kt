package com.kotlin.mywallet.finance

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.kotlin.mywallet.utils.Date

open class Cargo (private var amount: Float = 0.0f, private var category: String = "", private var note: String = ""):Parcelable {
    @RequiresApi(Build.VERSION_CODES.O)
    private val date: String = Date().now()

    open fun getAmount(): Float{
        return this.amount
    }

    open fun getCategory(): String{
        return this.category
    }

    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(amount)
        parcel.writeString(category)
        parcel.writeString(note)
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