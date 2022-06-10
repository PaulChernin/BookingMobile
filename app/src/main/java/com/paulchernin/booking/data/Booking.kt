package com.paulchernin.booking.data

import java.util.*

data class Booking(
    val id: Int,
    val roomId: Int,
    val dateStart: Date,
    val dateEnd: Date,
    val name: String,
    val surname: String,
    val deviceId: String
)