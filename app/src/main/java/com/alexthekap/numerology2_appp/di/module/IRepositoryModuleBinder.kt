package com.alexthekap.numerology2_appp.di.module

import com.alexthekap.numerology2_appp.data.repository.IPeopleRepository
import com.alexthekap.numerology2_appp.data.repository.PeopleRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface IRepositoryModuleBinder {

    @Binds
    @Singleton
    fun bindPeopleRepository(
        impl: PeopleRepository
    ): IPeopleRepository

}