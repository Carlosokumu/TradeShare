package com.example.smarttrader.data.local

import androidx.room.RoomDatabase
import com.example.smarttrader.data.local.dao.UserDao
import com.example.smarttrader.data.local.entity.User

@androidx.room.Database(
    entities = [
        User::class,
    ],
    version = 3,
    exportSchema = false
)


abstract class Database : RoomDatabase() {

    abstract fun userDao(): UserDao
}