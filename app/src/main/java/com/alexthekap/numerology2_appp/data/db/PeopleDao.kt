package com.alexthekap.numerology2_appp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexthekap.numerology2_appp.data.db.model.PersonModel
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(song: PersonModel): Single<Long>

    @Query("SELECT * FROM people_table ORDER BY dbId DESC")
    fun getPeople(): Flowable<List<PersonModel>>

    @Query("DELETE FROM people_table")
    fun deleteAllPeople(): Maybe<Int>

    @Query("SELECT * FROM people_table WHERE dbId = :id")
    fun getPersonById(id: Long): Flowable<PersonModel>

    @Query("DELETE FROM people_table WHERE dbId = :personId")
    fun deleteById(personId: Long): Single<Int>

//    @Query("UPDATE people_table SET img = :imgByteArr WHERE dbId = :id")
//    fun updateImg(imgByteArr: ByteArray, id: Long)

    @Query("UPDATE people_table SET note = :note WHERE dbId = :id")
    fun updateNote(note: String?, id: Long): Single<Int>

    @Query("UPDATE people_table SET personName = :name WHERE dbId = :id")
    fun updateName(name: String?, id: Long): Single<Int>

    @Query("UPDATE people_table SET birthDate = :bDate, dateChangedBirthDate = :changedAt WHERE dbId = :id")
    fun updateBDate(bDate: Long, id: Long, changedAt: Long): Single<Int>

    @Query("UPDATE people_table SET isFavorite = :favorite WHERE dbId = :id")
    fun updateIsFavorite(favorite: Boolean, id: Long): Single<Int>

    @Query("UPDATE people_table SET img = :imgByteArr WHERE dbId = :id")
    fun updateImg(imgByteArr: ByteArray?, id: Long): Single<Int>

    @Query("SELECT * FROM people_table WHERE personName LIKE :name ORDER BY dbId DESC")
    fun getPeopleWithName(name: String): Single<List<PersonModel>>

    @Query("SELECT * FROM people_table WHERE note LIKE :note ORDER BY dbId DESC")
    fun getPeopleWithNote(note: String): Single<List<PersonModel>>
}