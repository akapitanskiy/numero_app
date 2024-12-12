package com.alexthekap.numerology2_appp.di.module

import android.content.Context
import androidx.room.Room
import com.alexthekap.numerology2_appp.data.db.PeopleDao
import com.alexthekap.numerology2_appp.data.db.PeopleDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModuleProvider(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideDb(context: Context): PeopleDatabase {
        return Room.databaseBuilder(
            context,
            PeopleDatabase::class.java,
            PeopleDatabase.DB_FILE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideItunesDao(db: PeopleDatabase): PeopleDao {
        return db.getPeopleDao()
    }

}