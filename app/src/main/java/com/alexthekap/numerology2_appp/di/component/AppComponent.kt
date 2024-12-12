package com.alexthekap.numerology2_appp.di.component

import com.alexthekap.numerology2_appp.di.module.AppModuleProvider
import com.alexthekap.numerology2_appp.di.module.IRepositoryModuleBinder
import com.alexthekap.numerology2_appp.ui.people.PeopleViewModel
import com.alexthekap.numerology2_appp.ui.people.details.PersonDetailsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [
    AppModuleProvider::class,
    IRepositoryModuleBinder::class,
] )
interface AppComponent {

    fun plusViewModelComponent(): ViewModelComponent
    fun inject(viewModel: PeopleViewModel)
    fun inject(viewModel: PersonDetailsViewModel)
}