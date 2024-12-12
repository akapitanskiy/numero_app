package com.alexthekap.numerology2_appp.di

import android.content.Context
import com.alexthekap.numerology2_appp.di.component.AppComponent
import com.alexthekap.numerology2_appp.di.component.DaggerAppComponent
import com.alexthekap.numerology2_appp.di.component.ViewModelComponent
import com.alexthekap.numerology2_appp.di.module.AppModuleProvider

object ComponentManager {

    private lateinit var appComponent: AppComponent
    private var viewModelComponent: ViewModelComponent? = null

    fun initAppComponent(context: Context) {
        appComponent = DaggerAppComponent
            .builder()
            .appModuleProvider( AppModuleProvider(context) )
            .build()
    }

    fun getViewModelComponent(): ViewModelComponent {
        if (viewModelComponent == null) {
            viewModelComponent = appComponent.plusViewModelComponent()
        }
        return viewModelComponent!!
    }

    fun clearViewModelComponent() {
        viewModelComponent = null
    }

}