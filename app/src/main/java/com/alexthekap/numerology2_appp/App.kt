package com.alexthekap.numerology2_appp

import android.app.Application
import com.alexthekap.numerology2_appp.di.ComponentManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ComponentManager.initAppComponent(this)
    }
}