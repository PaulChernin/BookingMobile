package com.paulchernin.booking.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.Secure.ANDROID_ID
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.paulchernin.booking.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.image)
        imageView.setImageResource(R.drawable.house)

        val toFiltersButton = findViewById<Button>(R.id.to_filters_btn)
        toFiltersButton.setOnClickListener {
            val intent = Intent(this, FiltersActivity::class.java)
            startActivity(intent)
        }

        val toBookingsButton = findViewById<Button>(R.id.to_bookings_btn)
        toBookingsButton.setOnClickListener {
            val intent = Intent(this, MyBookingsListActivity::class.java)
            startActivity(intent)
        }
    }
}