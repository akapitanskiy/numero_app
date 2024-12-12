package com.alexthekap.numerology2_appp.di.module

import com.alexthekap.numerology2_appp.data.repository.IPeopleRepository
import com.alexthekap.numerology2_appp.ui.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class VMFactoryModuleProvider {

    @Provides
    fun provideViewModelFactory(
        peopleRepository: IPeopleRepository,
    ): ViewModelFactory {
        return ViewModelFactory(
            peopleRepository,
        )
    }

}