package com.example.smarttrader.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(indices = [Index(value = ["symbolId"], unique = true)])
data class SwingLightSymbol(
    @PrimaryKey
    var symbolId: Long,
    var baseAssetId: Long,
    var quoteAssetId: Long,
    var symbolName: String
)
