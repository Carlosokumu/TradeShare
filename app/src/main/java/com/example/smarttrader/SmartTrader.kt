package com.example.smarttrader

import android.app.Application
import com.example.core.di.coreModules
import com.example.smarttrader.di.appModules
import com.example.smarttrader.settings.Apppreference
import com.example.smarttrader.settings.Settings
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import org.koin.core.logger.Level
import org.koin.core.module.Module


class SmartTrader : Application() {


    private lateinit var apppreference: Apppreference


    override fun onCreate() {
        super.onCreate()
        initKoin()
        apppreference = Apppreference(this)
        Settings.init(this)



    }



    private fun initKoin() {
        try {
            startKoin {
                androidLogger(Level.ERROR)
                androidContext(applicationContext)//Application's context
                val modules = mutableListOf<Module>().apply {
                    addAll(appModules)
                    addAll(coreModules)

                }
                modules(modules)
            }
        } catch (error: KoinAppAlreadyStartedException) {

        }
    }



    fun getAppPreference() = apppreference



}