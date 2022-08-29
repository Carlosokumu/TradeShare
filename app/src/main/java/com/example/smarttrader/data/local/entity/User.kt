package com.example.smarttrader.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["username"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var firstname: String,
    var lastname: String,
    var email: String,
    var username: String,
    var floatingProfit: Double = 0.0,
    var equity: Double = 0.0,
    var balance: Double = 0.0,
    var password: String,
    var contribution: Double = 0.0,
    var phonenumber: String?)