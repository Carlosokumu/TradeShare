package com.carlos.core_database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.carlos.core_database.SeedData
import com.carlos.core_database.TradeShareDatabase
import com.carlos.core_database.daos.TradingPlatformDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


private val databaseModule: Module = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            TradeShareDatabase::class.java,
            "Tradeshare-database"
        ).fallbackToDestructiveMigration().addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    get<TradingPlatformDao>().upsertTradingPlatforms(SeedData.tradingPlatformEntityLists)
                }
            }
        }).build()
    }
}

private val daoModule: Module = module {
    single { get<TradeShareDatabase>().userDao() }
}

private val tradingPlatformDao: Module = module {
    single { get<TradeShareDatabase>().TradingPlatformDao() }
}


val databaseModules: List<Module> = listOf(
    tradingPlatformDao,
    databaseModule,
    daoModule
)