package com.paulchernin.booking.data

import android.os.Parcel
import android.os.Parcelable

data class Room(
    val id: Int,
    val name: String,
    val price: Int,
    val guestsNumber: Int,
    val withBreakfast: Boolean,
    val picture: String,
    val beds: String,
    val bookingId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(price)
        parcel.writeInt(guestsNumber)
        parcel.writeByte(if (withBreakfast) 1 else 0)
        parcel.writeString(picture)
        parcel.writeString(beds)
        parcel.writeInt(bookingId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Room> {
        override fun createFromParcel(parcel: Parcel): Room {
            return Room(parcel)
        }

        override fun newArray(size: Int): Array<Room?> {
            return arrayOfNulls(size)
        }
    }
}
