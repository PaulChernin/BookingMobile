package com.paulchernin.booking.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.paulchernin.booking.R
import com.paulchernin.booking.data.Booking
import com.paulchernin.booking.data.BookingRequest
import com.paulchernin.booking.data.Room
import com.paulchernin.booking.data.RoomRequest
import com.paulchernin.booking.utils.dateToLocalDate
import com.paulchernin.booking.utils.localDateToString
import com.squareup.picasso.Picasso
import java.time.Period

class MyBookingActivity : AppCompatActivity() {
    private lateinit var room: Room
    private lateinit var booking: Booking

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_booking)

        setupActionBar()
        findViews()

        room = intent.extras!!.getParcelable("room")!!

        val bookingRequest = BookingRequest(this)
        bookingRequest.get(room.bookingId) {
            booking = it

            showValues()
        }

        val cancelButton = findViewById<Button>(R.id.cancel_btn)
        cancelButton.setOnClickListener {
            cancelBooking(room.bookingId)

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    private lateinit var roomNameView: TextView
    private lateinit var imageView: ImageView
    private lateinit var roomsView: TextView
    private lateinit var datesView: TextView
    private lateinit var withBreakfastView: TextView
    private lateinit var costView: TextView
    private lateinit var namesView: TextView

    private fun findViews() {
        roomNameView = findViewById(R.id.room_name)
        imageView = findViewById(R.id.image)
        roomsView = findViewById(R.id.guestsNumber)
        datesView = findViewById(R.id.dates)
        withBreakfastView = findViewById(R.id.with_breakfast)
        costView = findViewById(R.id.cost)
        namesView = findViewById(R.id.names)
    }

    private fun showValues() {
        roomNameView.text = room.name
        Picasso.get().load(room.picture).into(imageView)
        roomsView.text = room.guestsNumber.toString() + " комнаты"

        val dateStartString = localDateToString(dateToLocalDate(booking.dateStart))
        val dateEndString = localDateToString(dateToLocalDate(booking.dateEnd))
        datesView.text = "С $dateStartString по $dateEndString"

        withBreakfastView.text =
            if (room.withBreakfast) "Завтрак включен"
            else "Завтрак не включен"

        val nights = Period.between(dateToLocalDate(booking.dateStart), dateToLocalDate(booking.dateEnd)).days
        val cost = room.price * nights
        costView.text = "К оплате: ${cost} р"

        namesView.text = "${booking.name} ${booking.surname}"
    }

    private fun setupActionBar() {
        actionBar?.title = "Вы забронировали этот номер"
        supportActionBar?.title = "Вы забронировали этот номер"

        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun cancelBooking(id: Int) {
        val bookingRequest = BookingRequest(this)
        bookingRequest.delete(id) {}
    }
}