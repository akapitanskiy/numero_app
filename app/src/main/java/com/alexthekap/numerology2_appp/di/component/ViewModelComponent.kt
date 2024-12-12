package com.alexthekap.numerology2_appp.di.component

import com.alexthekap.numerology2_appp.di.module.VMFactoryModuleProvider
import com.alexthekap.numerology2_appp.ui.MainActivity
import com.alexthekap.numerology2_appp.ui.people.NewPersonFragment
import com.alexthekap.numerology2_appp.ui.people.PeopleListFragment
import com.alexthekap.numerology2_appp.ui.people.details.NameEditBottomFragment
import com.alexthekap.numerology2_appp.ui.people.details.NoteEditFragment
import com.alexthekap.numerology2_appp.ui.people.details.PersonDetailsFragment
import com.alexthekap.numerology2_appp.ui.people.details.PythagoreanMatrixFragment
import com.alexthekap.numerology2_appp.ui.people.filter.OneFieldBottomFragment
import dagger.Subcomponent

@Subcomponent
    ( modules = [VMFactoryModuleProvider::class] )
interface ViewModelComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: PeopleListFragment)
    fun inject(fragment: NewPersonFragment)
    fun inject(fragment: PersonDetailsFragment)
    fun inject(fragment: PythagoreanMatrixFragment)
    fun inject(fragment: NoteEditFragment)
    fun inject(fragment: NameEditBottomFragment)
    fun inject(fragment: OneFieldBottomFragment)

}