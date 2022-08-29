package com.example.smarttrader.data.local.dao

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.smarttrader.data.local.entity.User

@Dao
interface UserDao: BaseDao<User> {

    @Query("SELECT * FROM User WHERE username =:username")
    suspend fun getUserByUsername(username: String): User

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(user: User): Int
}