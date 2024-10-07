package com.afapps.notificationSaver.core.di

import com.afapps.notificationSaver.core.shared.IOSKUPreferences
import com.afapps.notificationSaver.core.shared.Preferences
import com.afapps.notificationSaver.database.getDatabaseBuilder
import org.koin.dsl.module

actual val targetModule = module {
    single { getDatabaseBuilder() }
    single<Preferences> { IOSKUPreferences() }
}