package com.afapps.notificationSaver.core.di

import com.afapps.notificationSaver.core.local.NotificationDatabase
import com.afapps.notificationSaver.core.local.getRoomDatabase
import com.afapps.notificationSaver.data.repo.NotificationRepoImp
import com.afapps.notificationSaver.domain.repo.NotificationRepo
import com.afapps.notificationSaver.presentation.features.home.viewModel.HomeViewModel
import com.afapps.notificationSaver.presentation.features.settings.viewModel.SettingsViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.dsl.bind

expect val targetModule: Module

val sharedModule = module {
    single { getRoomDatabase(get()) }
    single { get<NotificationDatabase>().notificationDao() }
    single { get<NotificationDatabase>().subNotificationDao() }
    single { NotificationRepoImp(get(), get()) } bind NotificationRepo::class
    viewModelOf(::HomeViewModel)
    viewModel { SettingsViewModel(get()) }
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(targetModule, sharedModule)
    }
}