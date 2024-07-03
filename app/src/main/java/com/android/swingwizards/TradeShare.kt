package com.android.swingwizards

import android.app.Application
import com.example.core.di.coreModules
import com.android.swingwizards.di.appModules
import com.android.swingwizards.settings.AppPreference
import com.android.swingwizards.settings.Settings
import com.carlos.core_database.di.databaseModules
import com.carlos.data.di.coreDataModules
import com.carlos.network.di.netWorkModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import org.koin.core.logger.Level
import org.koin.core.module.Module


class TradeShare : Application() {


    private lateinit var apppreference: AppPreference


    override fun onCreate() {
        super.onCreate()
        initKoin()
        apppreference = AppPreference(this)
        Settings.init(this)
    }


    private fun initKoin() {
        try {
            startKoin {
                androidLogger(Level.ERROR)
                androidContext(applicationContext)
                val modules = mutableListOf<Module>().apply {
                    addAll(appModules)
                    addAll(coreModules)
                    addAll(coreDataModules)
                    addAll(netWorkModules)
                    addAll(databaseModules)

                }
                modules(modules)
            }
        } catch (error: KoinAppAlreadyStartedException) {

        }
    }


    fun getAppPreference() = apppreference


}