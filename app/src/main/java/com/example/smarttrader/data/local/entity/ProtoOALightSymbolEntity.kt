package com.example.smarttrader.data.local.entity

import androidx.room.Entity
import androidx.room.Index


@Entity(indices = [Index(value = ["symbolId"], unique = true)])
data class ProtoOALightSymbolEntity(
    val symbolId: Long,
    var symbolName: String,
    val baseAssetId: Long,
    val quoteAssetId: Long,
)
