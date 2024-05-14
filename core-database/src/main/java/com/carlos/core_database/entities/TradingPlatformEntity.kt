package com.carlos.core_database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TradingPlatformEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val logo: Int,
    var isSelected: Boolean = false
)