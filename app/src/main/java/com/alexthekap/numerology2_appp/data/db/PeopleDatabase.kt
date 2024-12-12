package com.alexthekap.numerology2_appp.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alexthekap.numerology2_appp.data.db.converter.Converter
import com.alexthekap.numerology2_appp.data.db.model.PersonModel

@Database(
    entities = [
        PersonModel::class
    ],
    version = 3,
    autoMigrations = [
        AutoMigration (from = 2, to = 3),
//        AutoMigration (from = 3, to = 4)
    ]
)
@TypeConverters(Converter::class)
abstract class PeopleDatabase : RoomDatabase() {

    companion object {
        const val DB_FILE_NAME = "people_numerology.db"
    }

    abstract fun getPeopleDao(): PeopleDao
}