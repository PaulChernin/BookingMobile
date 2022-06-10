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

        Log.d("mylog", pathGet(filter))

        db.getArray(pathGet(filter), context) {
            val roomsList = parseJson(it)

            callback(roomsList)
        }
    }

    fun get(deviceId: String, callback: (Array<Room>) -> Unit) {
        val db = DbAccess()

        Log.d("mylog", deviceId)

        db.getArray(pathGet(deviceId), context) {
            val roomsList = parseJson(it)

            callback(roomsList)
        }
    }

    fun put(room: Room, callback: () -> Unit) {
        val db = DbAccess()
        db.putObject(pathPut(room), makeJson(room), context, callback)
    }

    private fun pathPut(room: Room): String {
        return "/rooms/${room.id}"
    }

    private fun pathGet(filter: RoomFilter): String {
        val dateStartString = dateToString(filter.dateStart)
        val dateEndString = dateToString(filter.dateEnd)

        return "/rooms?guestsNumber=${filter.guestsNumber}&dateStart=${dateStartString}&dateEnd=${dateEndString}"
    }

    private fun pathGet(deviceId: String): String {
        return "/bookedRooms?deviceId=$deviceId"
    }

    private fun makeJson(room: Room): JSONObject {
        val gson = Gson()

        return JSONObject(gson.toJson(room))
    }

    private fun parseJson(jsonArray: JSONArray): Array<Room> {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        return Array(jsonArray.length()) {
            val jsonObject = jsonArray.get(it) as JSONObject
            gson.fromJson(jsonObject.toString(), Room::class.java)
        }

//        try {
//            return Array(jsonArray.length()) {
//                val jsonObject = jsonArray.get(it) as JSONObject
//                Room(
//                    jsonObject.getInt("id"),
//                    jsonObject.getString("name"),
//                    jsonObject.getInt("price"),
//                    jsonObject.getInt("rooms"),
//                    jsonObject.getBoolean("withBreakfast"),
//                    jsonObject.getString("picture"),
//                    jsonObject.optInt("bookingId")
//                )
//            }
//        } catch (e: Exception) {
//            Log.e("mylog", "Can't parse json")
//            return emptyArray()
//        }
    }
}