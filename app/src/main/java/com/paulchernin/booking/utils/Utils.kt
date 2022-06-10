package com.paulchernin.booking.utils

import android.text.Editable
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun localDateToString(date: LocalDate): String {
    return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
}

fun dateToString(date: Date): String {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    return dateFormatter.format(date)
}

fun stringToDate(string: String): Date {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.parse(string)!!
}

fun stringToEditable(string: String): Editable {
    return Editable.Factory.getInstance().newEditable(string)
}

fun localDateToDate(date: LocalDate): Date {
    val instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
    return Date.from(instant)
}

fun dateToLocalDate(date: Date): LocalDate {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
}