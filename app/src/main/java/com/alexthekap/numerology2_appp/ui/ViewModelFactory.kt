package com.alexthekap.numerology2_appp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexthekap.numerology2_appp.data.repository.IPeopleRepository
import com.alexthekap.numerology2_appp.ui.people.PeopleViewModel
import com.alexthekap.numerology2_appp.ui.people.details.PersonDetailsViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    peopleRepository: IPeopleRepository
) : ViewModelProvider.Factory {

    private val peopleRepository: IPeopleRepository

    init {
        this.peopleRepository = peopleRepository
    }

    @SuppressWarnings("unchecked")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return if (modelClass.isAssignableFrom(PeopleViewModel::class.java)) {
            PeopleViewModel(peopleRepository) as T
        } else if (modelClass.isAssignableFrom(PersonDetailsViewModel::class.java)) {
            PersonDetailsViewModel(peopleRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}