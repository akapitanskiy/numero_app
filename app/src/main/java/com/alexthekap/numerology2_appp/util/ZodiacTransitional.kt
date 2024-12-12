package com.alexthekap.numerology2_appp.util

import java.util.Calendar
import java.util.TimeZone

enum class ZodiacTransitional(val zodiacStr: String, val end: Calendar, val begin: Calendar) {

    AQUARIUS( "Aquarius",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 0, 21, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 0, 19, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    PISCES( "Pisces",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 1, 21, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 1, 18, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    ARIES( "Aries",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 2, 22, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 2, 20, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    TAURUS( "Taurus",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 3, 22, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 3, 19, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    GEMINI( "Gemini",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 4, 23, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 4, 20, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    CANCER( "Cancer",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 5, 23, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 5, 20, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    LEO( "Leo",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 6, 25, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 6, 22, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    VIRGO( "Virgo",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 7, 25, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 7, 21, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    LIBRA( "Libra",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 8, 25, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 8, 22, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    SCORPIO( "Scorpio",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 9, 25, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 9, 22, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    SAGITTARIUS( "Sagittarius",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 10, 24, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 10, 21, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    CAPRICORN( "Capricorn",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 11, 24, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 11, 21, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    )
}