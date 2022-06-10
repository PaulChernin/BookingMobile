package com.paulchernin.booking.data

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class DbAccess {
    private val baseUrl = "http://192.168.144.61:3000"
    private val onError: (VolleyError) -> Unit = { Log.i("mylog", "error") }

    fun getArray(path: String, context: Context, callback: (JSONArray) -> Unit) {
        val url = baseUrl + path

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null, callback, onError
        )

        val queue = Volley.newRequestQueue(context)
        queue.add(jsonArrayRequest)
    }

    fun getObject(path: String, context: Context, callback: (JSONObject) -> Unit) {
        val url = baseUrl + path

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null, callback, onError
        )

        val queue = Volley.newRequestQueue(context)
        queue.add(jsonObjectRequest)
    }

    fun putObject(path: String, payload: JSONObject, context: Context, callback: () -> Unit) {
        val url = baseUrl + path

        val request = JsonObjectRequest(
            Request.Method.PUT, url, payload, { callback() }, onError
        )

        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }

    fun postObject(path: String, payload: JSONObject, context: Context, callback: (Int) -> Unit) {
        val url = baseUrl + path

        val request = JsonObjectRequest(
            Request.Method.POST, url, payload, { callback(it.getInt("id")) }, onError
        )

        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }

    fun deleteObject(path: String, context: Context, callback: () -> Unit) {
        val url = baseUrl + path

        val request = JsonObjectRequest(
            Request.Method.DELETE, url, null, { callback() }, onError
        )

        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }
}