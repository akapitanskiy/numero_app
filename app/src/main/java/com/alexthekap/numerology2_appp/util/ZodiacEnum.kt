package com.alexthekap.numerology2_appp.util

import java.util.Calendar
import java.util.TimeZone

enum class ZodiacEnum(val zodiacStr: String, val calendar2: Calendar, val calendar1: Calendar) {

    AQUARIUS( "Aquarius",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 0, 21, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 0, 20, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    PISCES( "Pisces",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 1, 20, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 1, 19, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    ARIES( "Aries",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 2, 21, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 2, 21, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    TAURUS( "Taurus",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 3, 21, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 3, 20, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    GEMINI( "Gemini",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 4, 22, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 4, 21, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    CANCER( "Cancer",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 5, 22, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 5, 21, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    LEO( "Leo",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 6, 24, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 6, 23, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    VIRGO( "Virgo",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 7, 24, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 7, 22, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    LIBRA( "Libra",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 8, 24, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 8, 23, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    SCORPIO( "Scorpio",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 9, 24, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 9, 23, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    SAGITTARIUS( "Sagittarius",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 10, 23, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 10, 22, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    CAPRICORN( "Capricorn",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 11, 22, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2012, 11, 23, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    ),
    AQUARIUS2("Aquarius",
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2013, 0, 21, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        },
        Calendar.getInstance( TimeZone.getTimeZone("GMT") ).also{
            it.set(2013, 0, 20, 0, 0, 0); it.timeInMillis = it.timeInMillis/1000*1000
        }
    );
}