package com.afapps.notificationSaver.core.di

import com.afapps.notificationSaver.core.shared.AndroidKUPreferences
import com.afapps.notificationSaver.core.shared.PlatformContext
import com.afapps.notificationSaver.core.shared.Preferences
import com.afapps.notificationSaver.database.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val targetModule = module {
    single { getDatabaseBuilder(context = get()) }
    single { PlatformContext(androidContext()) }
    single<Preferences> { AndroidKUPreferences(androidContext()) }
}

