package com.afapps.managenotification.core.di

import com.afapps.managenotification.core.shared.IOSKUPreferences
import com.afapps.managenotification.core.shared.Preferences
import com.afapps.managenotification.database.getDatabaseBuilder
import org.koin.dsl.module

actual val targetModule = module {
    single { getDatabaseBuilder() }
    single<Preferences> { IOSKUPreferences() }
}