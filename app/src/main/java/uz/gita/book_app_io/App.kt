package uz.gita.book_app_io

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

// Created by Jamshid Isoqov an 10/9/2022
@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        instance = this
    }
}