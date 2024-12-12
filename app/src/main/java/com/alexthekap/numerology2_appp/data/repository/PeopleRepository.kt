package com.alexthekap.numerology2_appp.data.repository

import com.alexthekap.numerology2_appp.data.db.PeopleDao
import com.alexthekap.numerology2_appp.data.db.model.PersonModel
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val peopleDao: PeopleDao
) : IPeopleRepository {

    override
    fun getPeopleAndObserveDB(): Flowable<List<PersonModel>> {
        return peopleDao.getPeople()
    }

    override
    fun insertPersonIntoDb(person: PersonModel): Single<Long> {
        return peopleDao.insert(person)
    }

    override
    fun deleteAllPeopleFromDB(): Maybe<Int> {
        return peopleDao.deleteAllPeople()
    }

    override
    fun getPerson(id: Long): Flowable<PersonModel> {
        return peopleDao.getPersonById(id)
    }

    override
    fun deleteById(id: Long): Single<Int> {
        return peopleDao.deleteById(id)
    }

    override
    fun updateNote(id: Long, note: String?): Single<Int> {
        return peopleDao.updateNote(note, id)
    }

    override
    fun updateName(id: Long, note: String?): Single<Int> {
        return peopleDao.updateName(note, id)
    }

    override
    fun updateBirthdate(id: Long, bDateMillis: Long): Single<Int> {
        return peopleDao.updateBDate(bDateMillis, id, System.currentTimeMillis())
    }

    override
    fun updateIsFavorite(id: Long, isFavorite: Boolean): Single<Int> {
        return peopleDao.updateIsFavorite(isFavorite, id)
    }

    override fun updateImage(id: Long, imageArray: ByteArray?): Single<Int> {
        return peopleDao.updateImg(imageArray, id)
    }

    override
    fun filterByName(name: String): Single<List<PersonModel>> {
        return peopleDao.getPeopleWithName("%$name%")
    }

    override
    fun filterByNote(note: String): Single<List<PersonModel>> {
        return peopleDao.getPeopleWithNote("%$note%")
    }
}