package com.paulchernin.booking.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.paulchernin.booking.R
import com.paulchernin.booking.data.Room
import com.paulchernin.booking.data.RoomFilter
import com.paulchernin.booking.data.RoomRequest

class RoomsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms_list)

        setupActionBar()

        filter = intent.extras!!["filter"] as RoomFilter

        val request = RoomRequest(this)
        request.get(filter) {
            if (it.isNotEmpty()) {
                showList(it)
            } else {
                val noRoomsMessage = findViewById<TextView>(R.id.no_rooms_message)
                noRoomsMessage.visibility = View.VISIBLE
            }
        }
    }

    private lateinit var filter: RoomFilter

    private fun setupActionBar() {
        actionBar?.title = "Выберите номер"
        supportActionBar?.title = "Выберите номер"

        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun showList(roomsList: Array<Room>) {
        val listView = findViewById<ListView>(R.id.list_view)
        val adapter = RoomsListAdapter(this, roomsList) { onClick(it) }
        listView.adapter = adapter
    }

    private fun onClick(room: Room) {
        Log.d("mylog", filter.toString())

        val intent = Intent(this, NewBookingActivity::class.java)
        intent.putExtra("room", room)
        intent.putExtra("filter", filter)

        startActivity(intent)
    }
}