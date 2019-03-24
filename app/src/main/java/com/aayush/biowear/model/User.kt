package com.aayush.biowear.model

import android.os.Parcel
import android.os.Parcelable
import com.aayush.biowear.util.readDate
import com.aayush.biowear.util.readEnum
import com.aayush.biowear.util.writeDate
import com.aayush.biowear.util.writeEnum
import java.util.Date

data class User(
    var name: String,
    var birthday: Date,
    var height: Float,
    var weight: Float,
    var gender: Gender,
    var emergencyContact: String
): Parcelable {
    var bmi: Float = weight / (height * height / 10000f)

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readDate()!!,
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readEnum<Gender>()!!,
        parcel.readString()!!
    ) {
        bmi = parcel.readFloat()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeString(name)
        writeDate(birthday)
        writeFloat(height)
        writeFloat(weight)
        writeEnum(gender)
        writeString(emergencyContact)
        writeFloat(bmi)
    }

    override fun describeContents() = 0

    companion object {
        @JvmField val CREATOR = object: Parcelable.Creator<User> {
            override fun createFromParcel(parcel: Parcel) = User(parcel)

            override fun newArray(size: Int) = arrayOfNulls<User?>(size)
        }
    }
}