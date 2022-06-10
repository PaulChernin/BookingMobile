package com.paulchernin.booking.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.paulchernin.booking.utils.dateToString
import org.json.JSONArray
import org.json.JSONObject

class RoomRequest(
    private val context: Context
) {

    fun get(filter: RoomFilter, callback: (Array<Room>) -> Unit) {
        val db = DbAccess()

        db.getArray(pathGet(filter), context) {
            val roomsList = parseJson(it)

            callback(roomsList)
        }
    }

    fun get(deviceId: String, callback: (Array<Room>) -> Unit) {
        val db = DbAccess()

        db.getArray(pathGet(deviceId), context) {
            val roomsList = parseJson(it)

            callback(roomsList)
        }
    }

    private fun pathGet(filter: RoomFilter): String {
        val dateStartString = dateToString(filter.dateStart)
        val dateEndString = dateToString(filter.dateEnd)

        return "/rooms?guestsNumber=${filter.guestsNumber}&dateStart=${dateStartString}&dateEnd=${dateEndString}"
    }

    private fun pathGet(deviceId: String): String {
        return "/bookedRooms?deviceId=$deviceId"
    }

    private fun parseJson(jsonArray: JSONArray): Array<Room> {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        return Array(jsonArray.length()) {
            val jsonObject = jsonArray.get(it) as JSONObject
            gson.fromJson(jsonObject.toString(), Room::class.java)
        }

    }
}