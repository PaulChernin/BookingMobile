package com.paulchernin.booking.activities

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.paulchernin.booking.R
import com.paulchernin.booking.data.RoomFilter
import com.paulchernin.booking.utils.localDateToDate
import com.paulchernin.booking.utils.localDateToString
import com.paulchernin.booking.utils.stringToEditable
import java.time.LocalDate

class FiltersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)

        findViews()

        setupActionBar()

        setupDateDialogs()

        initValues()

        val toRoomsListButton = findViewById<Button>(R.id.to_rooms_list_btn)
        toRoomsListButton.setOnClickListener {
            val intent = Intent(this, RoomsListActivity::class.java)
            intent.putExtra("filter", makeFilter())

            startActivity(intent)
        }
    }

    private lateinit var guests1Chip: Chip
    private lateinit var dateStartView: TextView
    private lateinit var dateEndView: TextView

    private fun findViews() {
        guests1Chip = findViewById(R.id.guests1)
        dateStartView = findViewById(R.id.date_start)
        dateEndView = findViewById(R.id.date_end)
    }

    private fun initValues() {
        guests1Chip.isChecked = true
        dateStart = LocalDate.now()
        dateEnd = LocalDate.now().plusDays(1)
    }

    private fun setupActionBar() {
        actionBar?.title = "Найти номер"
        supportActionBar?.title = "Найти номер"

        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun getGuestsNumber(): Int? {
        val guestsView = findViewById<ChipGroup>(R.id.guestsNumber)

        return when (guestsView.checkedChipId) {
            R.id.guests1 -> 1
            R.id.guests2 -> 2
            R.id.guests3 -> 3
            R.id.guests4 -> 4
            else -> null
        }
    }

    private var dateStart = LocalDate.now()
        set(value) {
            dateStartView.text = stringToEditable(localDateToString(value))
            field = value
        }

    private var dateEnd = LocalDate.now()
        set(value) {
            dateEndView.text = stringToEditable(localDateToString(value))
            field = value
        }

    private fun setupDateDialogs() {
        dateStartView.setOnClickListener {
            pickDate(dateStart) { date ->
                dateStart = date
            }
        }

        dateEndView.setOnClickListener {
            pickDate(dateEnd) { date ->
                dateEnd = date
            }
        }
    }

    private fun pickDate(init: LocalDate, callback: (date: LocalDate) -> Unit) {
        val onDateSetListener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                val date = LocalDate.of(year, month + 1, day)

                callback(date)
            }

        val datePicker =
            DatePickerDialog(this, onDateSetListener, init.year, init.monthValue - 1, init.dayOfMonth)
        datePicker.show()
    }

    private fun makeFilter(): RoomFilter {
        Log.d("mylog", getGuestsNumber().toString())

        return RoomFilter(
            getGuestsNumber(),
            localDateToDate(dateStart),
            localDateToDate(dateEnd)
        )
    }
}