package com.afapps.managenotification

import android.app.Application
import com.afapps.managenotification.core.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class AndroidApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin {
            androidContext(this@AndroidApplication)
        }
    }
}