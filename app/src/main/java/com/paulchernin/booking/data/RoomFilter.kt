package com.paulchernin.booking.data

import android.os.Parcel
import android.os.Parcelable
import com.paulchernin.booking.utils.dateToString
import com.paulchernin.booking.utils.stringToDate
import java.util.*

data class RoomFilter(
    val guestsNumber: Int?,
    val dateStart: Date,
    val dateEnd: Date
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        stringToDate(parcel.readString()!!),
        stringToDate(parcel.readString()!!)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(guestsNumber)
        parcel.writeString(dateToString(dateStart))
        parcel.writeString(dateToString(dateEnd))
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RoomFilter> {
        override fun createFromParcel(parcel: Parcel): RoomFilter {
            return RoomFilter(parcel)
        }

        override fun newArray(size: Int): Array<RoomFilter?> {
            return arrayOfNulls(size)
        }
    }
}
