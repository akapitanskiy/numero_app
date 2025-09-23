package com.alexthekap.numerology2_appp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.alexthekap.numerology2_appp.di.ComponentManager

class App : Application() {

    companion object {
        private var instance: App? = null
        val inst get() = instance!!
    }

    override
    fun onCreate() {
        super.onCreate()
        ComponentManager.initAppComponent(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        instance = this
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }
}