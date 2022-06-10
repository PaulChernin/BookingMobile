package com.paulchernin.booking.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.Secure.ANDROID_ID
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.paulchernin.booking.R
import com.paulchernin.booking.data.BookingRequest
import com.paulchernin.booking.data.Room
import com.paulchernin.booking.data.RoomFilter
import com.paulchernin.booking.data.RoomRequest

class MyBookingsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings_list)

        setupActionBar()

        val request = RoomRequest(this)
        request.get(Settings.Secure.getString(getContentResolver(), ANDROID_ID)) {
            if (it.isNotEmpty()) {
                showList(it)
            } else {
                val noRoomsMessage = findViewById<TextView>(R.id.no_rooms_message)
                noRoomsMessage.visibility = View.VISIBLE
            }
        }
    }

    private fun setupActionBar() {
        actionBar?.title = "Мои бронирования"
        supportActionBar?.title = "Мои бронирования"

        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun showList(roomsList: Array<Room>) {
        val listView = findViewById<ListView>(R.id.list_view)

        val adapter = RoomsListAdapter(this, roomsList) { room ->

            val intent = Intent(this, MyBookingActivity::class.java)
            intent.putExtra("room", room)
            startActivity(intent)
        }

        listView.adapter = adapter
    }
}