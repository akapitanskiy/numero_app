package com.alexthekap.numerology2_appp.util

import java.util.Calendar
import java.util.TimeZone

object Zodiac {

//    private val CALENDAR = Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
//        it.timeInMillis = it.timeInMillis/1000*1000
//    }
//    private val CALENDAR2 = Calendar.getInstance(  ).also{
//        it.timeInMillis = it.timeInMillis/1000*1000
//    }
    private val aries = Calendar.getInstance().also{
        it.timeInMillis = it.timeInMillis/1000*1000
        it.set(2012, 3, 21, 0, 0, 0)
    }
    private val aries2 = Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
        it.timeInMillis = it.timeInMillis/1000*1000
        it.set(2012, 2, 21, 0, 0, 0)
    }

    fun getZodiacMain(timeMillis: Long): Pair<String,Int> {
        val cal = Calendar.getInstance( TimeZone.getTimeZone("GMT") )
        cal.timeInMillis = timeMillis
        cal.set(Calendar.YEAR, 2012)

        for (i in 0..< ZodiacEnum.entries.size - 1) {
            if (i == 11 && cal.get(Calendar.MONTH) == 0) {
                cal.set(Calendar.YEAR, 2013)
            }
            if (cal.after(ZodiacEnum.entries[i].calendar2) &&
                cal.before(ZodiacEnum.entries[i+1].calendar2))
            {
                return Pair(ZodiacEnum.entries[i].zodiacStr, cal.get(Calendar.MONTH))
            }
        }
        return Pair("ERROR. Unknown zodiac", -1)
    }

    fun getZodiac2(timeMillis: Long): String {
        val cal = Calendar.getInstance( TimeZone.getTimeZone("GMT") )
        cal.timeInMillis = timeMillis
        cal.set(Calendar.YEAR, 2012)

        for (i in 0..< ZodiacEnum.entries.size - 1) {
            if (i == 11 && cal.get(Calendar.MONTH) == 0) {
                cal.set(Calendar.YEAR, 2013)
            }
            if (cal.after(ZodiacEnum.entries[i].calendar1) &&
                cal.before(ZodiacEnum.entries[i+1].calendar1) )
            {
                return ZodiacEnum.entries[i].zodiacStr
            }
        }
        return "ERROR. Unknown zodiac"
    }

    fun isTransitional(timeMillis: Long): Boolean {
        val cal = Calendar.getInstance( TimeZone.getTimeZone("GMT") )
        cal.timeInMillis = timeMillis
        cal.set(Calendar.YEAR, 2012)

        ZodiacTransitional.entries.forEach {
            if (cal.after(it.begin) && cal.before(it.end))
                return true
        }
        return false
    }
}