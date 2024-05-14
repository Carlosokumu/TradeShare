package com.carlos.core_database

import androidx.room.RoomDatabase
import com.carlos.core_database.daos.TradingPlatformDao
import com.carlos.core_database.daos.UserDao
import com.carlos.core_database.entities.TradingPlatformEntity
import com.carlos.core_database.entities.UserEntity

@androidx.room.Database(
    entities = [
        UserEntity::class,
        TradingPlatformEntity::class
    ],
    version = 1,
    exportSchema = true
)


abstract class TradeShareDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun TradingPlatformDao(): TradingPlatformDao
}