package me.ruslan1024.kk2048game.app

import android.app.Application
import androidx.viewbinding.BuildConfig
import timber.log.Timber

class App : Application() {
    companion object {
        lateinit var instance: App
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }


}