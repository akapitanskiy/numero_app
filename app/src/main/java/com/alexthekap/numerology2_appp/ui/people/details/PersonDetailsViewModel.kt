package com.alexthekap.numerology2_appp.ui.people.details

import android.content.Context
import android.net.Uri
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
import com.alexthekap.numerology2_appp.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class PersonDetailsViewModel @Inject constructor(
    private val repository: IPeopleRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val TAG = this::class.java.simpleName
    private val personLD = MutableLiveData<PersonModel?>()

    private fun getPerson(id: Long) {
        repository.getPerson(id)
            .subscribeOn(Schedulers.io())
            .subscribe(
                personLD::postValue
            ) { Log.d(TAG, "getPerson: ERROR ${it.message}") }
            .also{ disposable.add(it) }
    }

    fun updateNote(note: String?, id: Long?) {
        id ?: return
        repository.updateNote(id, note)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Log.d(TAG, "updateNote: updated $it")},
                { Log.d(TAG, "updateNote: ERROR ${it.message}")}
            )
            .also{ disposable.add(it) }
    }

    fun updateNameAndNavigateToDetails(name: String?, id: Long?, navController: NavController) {
        id ?: return
        repository.updateName(id, name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(TAG, "updateNote: updated $it")
                    navController.navigate(
                        R.id.personDetailsFragmentId,
                        Bundle().apply{
                            putLong(ARG_PERSON_DB_ID, id)
                            putString(ARG_PERSON_DB_NAME, name)
                        },
                        NavOptions.Builder().setPopUpTo(R.id.peopleListFragmentId, false).build()
                    )
                },
                { Log.d(TAG, "updateNote: ERROR ${it.message}") }
            )
            .also{ disposable.add(it) }
    }

    fun updateBirthdate(year: Int, month: Int, day: Int, id: Long) {
        repository.updateBirthdate( id, Utils.getMillis(year, month, day) )
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Log.d(TAG, "updateBirthdate: updated $it") },
                { Log.d(TAG, "updateBirthdate: ERROR ${it.message}") }
            )
            .also{ disposable.add(it) }
    }


    fun resetPersonLD() {
        personLD.setValue(null)
    }

    fun onFavoriteClick() {
        personLD.value ?: return
        repository.updateIsFavorite(personLD.value!!.dbId, !personLD.value!!.isFavorite)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Log.d(TAG, "updateIsFavorite: updated $it") },
                { Log.d(TAG, "updateIsFavorite: ERROR ${it.message}") }
            )
            .also{ disposable.add(it) }
    }

    fun onFileObtained(uri: Uri?, context: Context, dbId: Long?) {
        uri ?: return
        dbId ?: return
        repository.updateImage(dbId, readBytes(uri, context))
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Log.d(TAG, "updateImage: updated $it") },
                { Log.d(TAG, "updateImage: ERROR ${it.message}") }
            )
            .also{ disposable.add(it) }
    }

    fun removePhoto(dbId: Long?) {
        dbId ?: return
        repository.updateImage(dbId, null)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Log.d(TAG, "updateImage: updated $it") },
                { Log.d(TAG, "updateImage: ERROR ${it.message}") }
            )
            .also{ disposable.add(it) }
    }

    private fun readBytes(uri: Uri?, context: Context): ByteArray? {
        uri ?: return null
        var inputStream: InputStream? = null
        var byteArrayOutputStream: ByteArrayOutputStream? = null

        try {
            inputStream = context.contentResolver.openInputStream(uri)
            byteArrayOutputStream = ByteArrayOutputStream()

            if (inputStream != null) {
                val buffer = ByteArray(1024)
                var bytesRead: Int

                while (true) {
                    bytesRead = inputStream.read(buffer)
                    if (bytesRead == -1) break
                    byteArrayOutputStream.write(buffer, 0, bytesRead)
                }
                return byteArrayOutputStream.toByteArray()
            }
        } catch (e: IOException) {
            Log.e(TAG, "readBytes: ERROR", e)
        } finally {
            inputStream?.close()
            byteArrayOutputStream?.close()
        }
        return null
    }

    fun getPersonLd(id: Long): LiveData<PersonModel?> {
        getPerson(id)
        return personLD
    }
}