package com.afapps.managenotification.core.di

import com.afapps.managenotification.core.shared.AndroidKUPreferences
import com.afapps.managenotification.core.shared.PlatformContext
import com.afapps.managenotification.core.shared.Preferences
import com.afapps.managenotification.database.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val targetModule = module {
    single { getDatabaseBuilder(context = get()) }
    single { PlatformContext(androidContext()) }
    single<Preferences> { AndroidKUPreferences(androidContext()) }
}

