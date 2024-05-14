package com.carlos.core_database.daos

import androidx.room.Dao
import androidx.room.Query
import com.carlos.core_database.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM UserEntity WHERE username =:username")
    fun getUserByUsername(username: String): UserEntity


}