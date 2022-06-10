package com.paulchernin.booking.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject

class BookingRequest(
    private val context: Context
) {

    fun get(id: Int, callback: (Booking) -> Unit) {

        val db = DbAccess()
        val path = "/bookings/$id"
        db.getObject(path, context) {
            val booking = parseJson(it)

            callback(booking)
        }
    }

    fun post(booking: Booking, callback: (Int) -> Unit) {
        val db = DbAccess()
        db.postObject(pathPost(), makeJson(booking), context, callback)
    }

    fun delete(id: Int, callback: () -> Unit) {
        val db = DbAccess()
        val path = "/bookings/$id"
        db.deleteObject(path, context, callback)
    }

    private fun pathPost(): String {
        return "/booking"
    }

    private fun makeJson(booking: Booking): JSONObject {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        return JSONObject(gson.toJson(booking))
    }

    private fun parseJson(jsonObject: JSONObject): Booking {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        return gson.fromJson(jsonObject.toString(), Booking::class.java)
    }
}