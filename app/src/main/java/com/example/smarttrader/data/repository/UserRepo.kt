package com.example.smarttrader.data.repository

import com.example.smarttrader.data.local.dao.UserDao
import com.example.smarttrader.data.local.entity.User

class UserRepo(private val userDao: UserDao) {

    suspend fun saveUser(user: User) = userDao.insert(user)


    suspend fun getUserByUsername(username: String): User =
        userDao.getUserByUsername(username)


    suspend fun  updateUserInfo(user: User) =  userDao.update(user)
}