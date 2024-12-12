package com.alexthekap.numerology2_appp.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converter {

    @TypeConverter
    fun listToJsonString(value: List<Int>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()
}