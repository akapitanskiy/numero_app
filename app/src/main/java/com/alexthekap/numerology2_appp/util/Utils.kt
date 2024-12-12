package com.alexthekap.numerology2_appp.util

import java.text.SimpleDateFormat
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


}