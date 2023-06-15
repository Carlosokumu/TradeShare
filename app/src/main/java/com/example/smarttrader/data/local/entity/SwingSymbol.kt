package com.example.smarttrader.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(indices = [Index(value = ["symbolId"], unique = true)])
data class SwingSymbol(
    @PrimaryKey
    var symbolId: Long,
    var digits: Int,
    var pipPosition: Int
)