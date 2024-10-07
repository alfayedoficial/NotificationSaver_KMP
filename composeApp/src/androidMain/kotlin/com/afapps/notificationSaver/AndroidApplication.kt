package com.afapps.notificationSaver

import android.app.Application
import com.afapps.notificationSaver.core.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class AndroidApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin {
            androidContext(this@AndroidApplication)
        }
    }
}