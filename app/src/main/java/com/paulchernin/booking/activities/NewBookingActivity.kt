package com.paulchernin.booking.activities

import android.app.DatePickerDialog
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.Secure.ANDROID_ID
import android.view.MenuItem
import android.widget.*
import androidx.core.app.NotificationCompat
import com.paulchernin.booking.R
import com.paulchernin.booking.data.*
import com.paulchernin.booking.utils.dateToLocalDate
import com.paulchernin.booking.utils.localDateToDate
import com.paulchernin.booking.utils.localDateToString
import com.paulchernin.booking.utils.stringToEditable
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.Period

class NewBookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_booking)

        findViews()

        setupActionBar()

        val params = intent.extras!!
        room = params.getParcelable("room")!!
        filter = params.getParcelable("filter")!!

        showValues()

        bookButton.setOnClickListener {
            book()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            overridePendingTransition(0, android.R.anim.slide_out_right)
        }
    }

    private lateinit var room: Room
    private lateinit var filter: RoomFilter

    private lateinit var roomNameView: TextView
    private lateinit var imageView: ImageView
    private lateinit var roomsView: TextView
    private lateinit var withBreakfastView: TextView
    private lateinit var costView: TextView
    private lateinit var datesView: TextView
    private lateinit var nameView: TextView
    private lateinit var surnameView: TextView
    private lateinit var bookButton: Button

    private fun findViews() {
        roomNameView = findViewById(R.id.room_name)
        roomsView = findViewById(R.id.guestsNumber)
        imageView = findViewById(R.id.image)
        withBreakfastView = findViewById(R.id.with_breakfast)
        costView = findViewById(R.id.cost)
        datesView = findViewById(R.id.dates)
        nameView = findViewById(R.id.name)
        surnameView = findViewById(R.id.surname)
        bookButton = findViewById(R.id.book_btn)
    }

    private fun showValues() {
        roomNameView.text = room.name

        Picasso.get().load(room.picture).fit().centerCrop().into(imageView)

        roomsView.text = room.guestsNumber.toString() + " комнаты"

        val dateStartString = localDateToString(dateToLocalDate(filter.dateStart))
        val dateEndString = localDateToString(dateToLocalDate(filter.dateEnd))
        datesView.text = "С $dateStartString по $dateEndString"

        withBreakfastView.text =
            if (room.withBreakfast) "Завтрак включен"
            else "Завтрак не включен"

        showComputedCost()
    }

    private fun showComputedCost() {
        val nights = Period.between(dateToLocalDate(filter.dateStart), dateToLocalDate(filter.dateEnd)).days

        if (nights < 1) {
            costView.text = "Неправильно введены даты"
            return
        }

        val cost = room.price * nights
        costView.text = "К оплате: ${cost} р"
    }

    private fun setupActionBar() {
        actionBar?.title = "Забронировать номер"
        supportActionBar?.title = "Забронировать номер"

        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun book() {
        val booking = Booking(
            0,
            room.id,
            filter.dateStart,
            filter.dateEnd,
            nameView.text.toString(),
            surnameView.text.toString(),
            Settings.Secure.getString(getContentResolver(), ANDROID_ID)
        )

        val bookingRequest = BookingRequest(this)
        bookingRequest.post(booking) {}
    }
}