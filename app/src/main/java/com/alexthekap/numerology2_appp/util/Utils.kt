package com.alexthekap.numerology2_appp.util

import android.os.Build
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Utils {

    private val DATE_FORMAT_DOT = SimpleDateFormat("dd.MM.yyyy", Locale.US)
    private val DATE_FORMAT_MONTH = SimpleDateFormat("dd MMM yyyy", Locale.US)
    private val DATE_FORMAT_NO_DIVIDER = SimpleDateFormat("ddMMyyyy", Locale.US)

    fun longToStrDate(timeInMillis: Long?): String {
        timeInMillis ?: return ""
        return DATE_FORMAT_MONTH.format( Date(timeInMillis) )
    }

    fun longToStrDateNoSeparator(timeInMillis: Long): String {
        return DATE_FORMAT_NO_DIVIDER.format( Date(timeInMillis) )
    }


    fun getMillis(year: Int, month: Int, day: Int): Long {
        val calendar = Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(year, month, day, 0, 0, 0)
            it.timeInMillis = it.timeInMillis/1000*1000 + 1
        }
        return calendar.timeInMillis
    }

    fun getAge(birthMillis: Long): Int {
        val birthDate = Calendar.getInstance().apply{ timeInMillis = birthMillis }
        val today = Calendar.getInstance()

        var age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age
    }

    fun getAge2(birthMillis: Long): Int {
        val birthDate = if (Build.VERSION.SDK_INT >= 26) {
            Instant.ofEpochMilli(birthMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        return ChronoUnit.YEARS.between(birthDate, LocalDate.now()).toInt()
    }
}