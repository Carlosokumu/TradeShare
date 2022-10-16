package com.example.smarttrader.data.local.mapper

import com.example.core.network.data.models.CurrentUserInfo
import com.example.core.network.data.models.RegisteredUser
import com.example.smarttrader.data.local.entity.User


fun RegisteredUser.toUser() = User(id = 0,username = this.user.username, email = this.user.email, phonenumber = null, firstname = this.user.firstname, lastname = this.user.lastname, password = this.user.password)



fun CurrentUserInfo.toUser() = User(
    id = 0,
    username = this.user.username,
    email = this.user.email,
    phonenumber = this.user.phonenumber,
    firstname = this.user.firstname,
    lastname = this.user.username,
    password = this.user.password,
    equity = this.user.equity,
    balance = this.user.balance,
    floatingProfit = this.user.floatingprofit,
    contribution = this.user.contribution
)