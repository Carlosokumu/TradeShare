package com.example.core.network.data.models

import com.google.gson.annotations.SerializedName

data class MtPositions(
    val positions: List<MtPosition>
)


data class MtPosition(
    @SerializedName("brokerTime")
    val entryTime: String,
    @SerializedName("openPrice")
    val entryPrice: Double,
    val stopLoss: Double?,
    val takeProfit: Double?,
    val comment: String?,
    val symbol: String
)