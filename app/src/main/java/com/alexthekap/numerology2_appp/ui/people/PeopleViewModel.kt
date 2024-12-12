package com.alexthekap.numerology2_appp.ui.people

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.alexthekap.numerology2_appp.R
import com.alexthekap.numerology2_appp.data.db.model.PersonModel
import com.alexthekap.numerology2_appp.data.repository.IPeopleRepository
import com.alexthekap.numerology2_appp.ui.people.details.ARG_BIRTHDAY_DATE
import com.alexthekap.numerology2_appp.ui.people.details.ARG_PERSON_NAME
import com.alexthekap.numerology2_appp.util.Matrix
import com.alexthekap.numerology2_appp.util.Utils
import com.alexthekap.numerology2_appp.util.Zodiac
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PeopleViewModel @Inject constructor(
    private val repository: IPeopleRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val TAG = this::class.java.simpleName
    private val peopleLD = MutableLiveData<List<PersonModel>>()
    private val sortedFavoriteLD = MutableLiveData(false)

    private fun getPeopleAndObserveDb() {
        repository.getPeopleAndObserveDB()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { onGetPeopleAndObserveDBSuccess(it) },
                { Log.d(TAG, "getPeopleAndObserveDB: ${it.message}") }
            )
            .also{ disposable.add(it) }
    }

    fun deleteAllFromDb() {
        repository.deleteAllPeopleFromDB()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Log.d(TAG, "deleteAllFromDb: deleted $it items")},
                { Log.d(TAG, "deleteAllFromDb: ERROR ${it.message}")}
            )
            .also{ disposable.add(it) }
    }

    fun deletePersonById(id: Long) {
        repository.deleteById(id)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Log.d(TAG, "deletePersonById: deleted $it items")},
                { Log.d(TAG, "deletePersonById: ERROR ${it.message}")}
            )
            .also{ disposable.add(it) }
    }

    fun filterByName(name: String?) {
        name ?: return
        repository.filterByName(name.trim())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Log.d(TAG, "filterByName: filtered ${it.size} items")
                    peopleLD.postValue(it)
                },
                { Log.d(TAG, "filterByName: ERROR ${it.message}")}
            )
            .also{ disposable.add(it) }
    }

    fun filterByNote(note: String?) {
        note ?: return
        repository.filterByNote(note.trim())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Log.d(TAG, "filterByNote: filtered ${it.size} items")
                    peopleLD.postValue(it)
                },
                { Log.d(TAG, "filterByNote: ERROR ${it.message}")}
            )
            .also{ disposable.add(it) }
    }

    private fun onGetPeopleAndObserveDBSuccess(people: List<PersonModel>) {
        if (sortedFavoriteLD.value == true) {
            peopleLD.postValue(people.sortedBy{ p -> !p.isFavorite })
        } else {
            peopleLD.postValue(people)
        }
    }

    fun orderFavorite() {
        peopleLD.value ?: return
        sortedFavoriteLD.setValue(sortedFavoriteLD.value != true)
        peopleLD.setValue(
            if (sortedFavoriteLD.value == true) {
                peopleLD.value!!.sortedBy{ !it.isFavorite }
            } else {
                peopleLD.value!!.sortedByDescending{ it.dbId }
            }
        )
    }

    fun reload() {
        getPeopleAndObserveDb()
    }

    fun insertPerson(year: Int, month: Int, day: Int, name: String?, note: String?, nav: NavController) {
        name ?: return
        val birthdateMillis = Utils.getMillis(year, month, day)
        val person = PersonModel(
            name,
            birthdateMillis,
            note,
            System.currentTimeMillis(),
            Matrix(birthdateMillis).numberList,
            zodiac = Zodiac.getZodiacMain(birthdateMillis).first
        )

        repository.insertPersonIntoDb(person)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(TAG, "insert: inserted $it")
                    nav.navigate(
                        R.id.pythagoreanMatrixFragmentId,
                        Bundle().apply{
                            putLong(ARG_BIRTHDAY_DATE, birthdateMillis)
                            putString(ARG_PERSON_NAME, name)
                        },
                        NavOptions.Builder()
                            .setPopUpTo(R.id.peopleListFragmentId, false)
                            .build()
                    )
                },
                { Log.d(TAG, "insert error: ${it.message}") }
            )
            .also{ disposable.add(it) }
    }

    fun getPeopleLd(): LiveData<List<PersonModel>> {
        if (peopleLD.value == null) {
            getPeopleAndObserveDb()
        }
        return peopleLD
    }

    fun getSortedFavoriteLD(): LiveData<Boolean> {
        return sortedFavoriteLD
    }

    override
    fun onCleared() {
        disposable.clear()
    }
}