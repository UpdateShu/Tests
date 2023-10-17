package com.geekbrains.tests

import android.app.Application
import di.module
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
//			androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
//			androidContext(this@App)
            modules(module)
        }
    }
}