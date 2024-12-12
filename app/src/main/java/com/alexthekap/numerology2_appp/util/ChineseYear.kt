package com.alexthekap.numerology2_appp.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object ChineseYear {

    private val yearsStrList = listOf(
        "Feb 05 1924", //"Jan 23 1925",
        "Jan 24 1925", //"Feb 12 1926",
        "Feb 13 1926", //"Feb 01 1927",
        "Feb 02 1927", //"Jan 22 1928",
        "Jan 23 1928", //"Feb 09 1929",
        "Feb 10 1929", //"Jan 29 1930",
        "Jan 30 1930", //"Feb 16 1931",
        "Feb 17 1931", //"Feb 05 1932",
        "Feb 06 1932", //"Jan 25 1933",
        "Jan 26 1933", //"Feb 13 1934",
        "Feb 14 1934", //"Feb 03 1935",
        "Feb 04 1935", //"Jan 23 1936",
        "Jan 24 1936", //"Feb 10 1937",
        "Feb 11 1937", //"Jan 30 1938",
        "Jan 31 1938", //"Feb 18 1939",
        "Feb 19 1939", //"Feb 07 1940",
        "Feb 08 1940", //"Jan 26 1941",
        "Jan 27 1941", //"Feb 14 1942",
        "Feb 15 1942", //"Feb 04 1943",
        "Feb 05 1943", //"Jan 24 1944",
        "Jan 25 1944", //"Feb 12 1945",
        "Feb 13 1945", //"Feb 01 1946",
        "Feb 02 1946", //"Jan 21 1947",
        "Jan 22 1947", //"Feb 09 1948",
        "Feb 10 1948", //"Jan 28 1949",
        "Jan 29 1949", //"Feb 16 1950",
        "Feb 17 1950", //"Feb 05 1951",
        "Feb 06 1951", //"Jan 26 1952",
        "Jan 27 1952", //"Feb 13 1953",
        "Feb 14 1953", //"Feb 02 1954",
        "Feb 03 1954", //"Jan 23 1955",
        "Jan 24 1955", //"Feb 11 1956",
        "Feb 12 1956", //"Jan 30 1957",
        "Jan 31 1957", //"Feb 17 1958",
        "Feb 18 1958", //"Feb 07 1959",
        "Feb 08 1959", //"Jan 27 1960",
        "Jan 28 1960", //"Feb 14 1961",
        "Feb 15 1961", //"Feb 04 1962",
        "Feb 05 1962", //"Jan 24 1963",
        "Jan 25 1963", //"Feb 12 1964",
        "Feb 13 1964", //"Feb 01 1965",
        "Feb 02 1965", //"Jan 20 1966",
        "Jan 21 1966", //"Feb 08 1967",
        "Feb 09 1967", //"Jan 29 1968",
        "Jan 30 1968", //"Feb 16 1969",
        "Feb 17 1969", //"Feb 05 1970",
        "Feb 06 1970", //"Jan 26 1971",
        "Jan 27 1971", //"Feb 14 1972",
        "Feb 15 1972", //"Feb 02 1973",
        "Feb 03 1973", //"Jan 22 1974",
        "Jan 23 1974", //"Feb 10 1975",
        "Feb 11 1975", //"Jan 30 1976",
        "Jan 31 1976", //"Feb 17 1977",
        "Feb 18 1977", //"Feb 06 1978",
        "Feb 07 1978", //"Jan 27 1979",
        "Jan 28 1979", //"Feb 15 1980",
        "Feb 16 1980", //"Feb 04 1981",
        "Feb 05 1981", //"Jan 24 1982",
        "Jan 25 1982", //"Feb 12 1983",
        "Feb 13 1983", //"Feb 01 1984",
        "Feb 02 1984", //"Feb 19 1985",
        "Feb 20 1985", //"Feb 08 1986",
        "Feb 09 1986", //"Jan 28 1987",
        "Jan 29 1987", //"Feb 16 1988",
        "Feb 17 1988", //"Feb 05 1989",
        "Feb 06 1989", //"Jan 26 1990",
        "Jan 27 1990", //"Feb 14 1991",
        "Feb 15 1991", //"Feb 03 1992",
        "Feb 04 1992", //"Jan 22 1993",
        "Jan 23 1993", //"Feb 09 1994",
        "Feb 10 1994", //"Jan 30 1995",
        "Jan 31 1995", //"Feb 18 1996",
        "Feb 19 1996", //"Feb 06 1997",
        "Feb 07 1997", //"Jan 27 1998",
        "Jan 28 1998", //"Feb 15 1999",
        "Feb 16 1999", //"Feb 04 2000",
        "Feb 05 2000", //"Jan 23 2001",
        "Jan 24 2001", //"Feb 11 2002",
        "Feb 12 2002", //"Jan 31 2003",
        "Feb 01 2003", //"Jan 21 2004",
        "Jan 22 2004", //"Feb 08 2005",
        "Feb 09 2005", //"Jan 28 2006",
        "Jan 29 2006", //"Feb 17 2007",
        "Feb 18 2007", //"Feb 06 2008",
        "Feb 07 2008", //"Jan 25 2009",
        "Jan 26 2009", //"Feb 13 2010",
        "Feb 14 2010", //"Feb 02 2011",
        "Feb 03 2011", //"Jan 22 2012",
        "Jan 23 2012", //"Feb 09 2013",
        "Feb 10 2013", //"Jan 30 2014",
        "Jan 31 2014", //"Feb 18 2015",
        "Feb 19 2015", //"Feb 07 2016",
        "Feb 08 2016", //"Jan 27 2017",
        "Jan 28 2017", //"Feb 15 2018",
        "Feb 16 2018", //"Feb 04 2019",
        "Feb 05 2019", //"Jan 24 2020",
        "Jan 25 2020", //"Feb 11 2021",
        "Feb 12 2021", //"Jan 31 2022",
        "Feb 01 2022", //"Jan 21 2023",
        "Jan 22 2023", //"Feb 09 2024",
        "Feb 10 2024", //"Jan 28 2025",
        "Jan 29 2025", //"Feb 16 2026",
        "Feb 17 2026", //"Feb 05 2027",
        "Feb 06 2027", //"Jan 25 2028",
        "Jan 26 2028", //"Feb 12 2029",
        "Feb 13 2029", //"Feb 02 2030",
        "Feb 03 2030", //"Jan 22 2031",
        "Jan 23 2031", //"Feb 10 2032",
        "Feb 11 2032", //"Jan 30 2033",
        "Jan 31 2033", //"Feb 18 2034",
        "Feb 19 2034", //"Feb 07 2035",
        "Feb 08 2035", //"Jan 27 2036",
        "Jan 28 2036", //"Feb 14 2037",
        "Feb 15 2037", //"Feb 03 2038",
        "Feb 04 2038", //"Jan 23 2039",
        "Jan 24 2039", //"Feb 11 2040",
        "Feb 12 2040", //"Jan 31 2041",
        "Feb 01 2041", //"Jan 21 2042",
        "Jan 22 2042", //"Feb 09 2043",
        "Feb 10 2043" //"Jan 29 2044"
    )

    private val yearsLongList: List<Long>
    private val DATE_FORMAT_SPACE = SimpleDateFormat("MMM dd yyyy", Locale.US)
    private val yearsNameArray: Array<String> = arrayOf("Rat", "Bull.Ox", "Tiger", "Rabbit.Cat", "Dragon", "Snake", "Horse", "Goat", "Monkey", "Rooster", "Dog", "Pig")

    init {
        yearsLongList = yearsStrList.map {
            DATE_FORMAT_SPACE.parse(it)?.time ?: 0
        }
    }

    fun getChineseYear(timeMillis: Long): Pair<String,String?> {
        val cal = Calendar.getInstance( TimeZone.getTimeZone("GMT") )
        cal.timeInMillis = timeMillis
        val year = cal.get(Calendar.YEAR).toString()
        var yearStr: String? = null

        for (i in 0..< yearsLongList.size - 1) {
            if (timeMillis > yearsLongList[i] && timeMillis < yearsLongList[i+1]) {
                if (yearsStrList.get(i).contains(year)) {
                    yearStr = yearsStrList.get(i)
                } else if (yearsStrList.get(i+1).contains(year)) {
                    yearStr = yearsStrList.get(i+1)
                }
                return Pair(yearsNameArray[i%12], yearStr)
            }
        }
        return Pair("Undefined", "Err year")
    }

}