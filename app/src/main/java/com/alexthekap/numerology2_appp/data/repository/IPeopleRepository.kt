package com.alexthekap.numerology2_appp.data.repository

import com.alexthekap.numerology2_appp.data.db.model.PersonModel
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface IPeopleRepository {

    fun getPeopleAndObserveDB(): Flowable<List<PersonModel>>

    fun insertPersonIntoDb(person: PersonModel): Single<Long>

    fun deleteAllPeopleFromDB(): Maybe<Int>

    fun getPerson(id: Long): Flowable<PersonModel>

    fun deleteById(id: Long): Single<Int>

    fun updateNote(id: Long, note: String?): Single<Int>

    fun updateName(id: Long, note: String?): Single<Int>

    fun updateBirthdate(id: Long, bDateMillis: Long): Single<Int>

    fun updateImage(id: Long, imageArray: ByteArray?): Single<Int>

    fun updateIsFavorite(id: Long, isFavorite: Boolean): Single<Int>

    fun filterByName(name: String): Single<List<PersonModel>>

    fun filterByNote(note: String): Single<List<PersonModel>>
}