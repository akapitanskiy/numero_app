package com.alexthekap.numerology2_appp.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "people_table",
//    indices = [Index(value = ["personName"], unique = true)]
)
data class PersonModel(

    val personName: String,

    val birthDate: Long,

    val note: String? = null,

    val dateAddedMillis: Long,

    val numberList: List<Int>,

    var dateChangedBirthDate: Long? = null,

    var zodiac: String? = null,

    var chineseYear: String? = null,

    @ColumnInfo(defaultValue = "false")
    var isFavorite: Boolean = false

) {
    @PrimaryKey(autoGenerate = true)
    var dbId: Long = 0

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var img: ByteArray? = null

}
