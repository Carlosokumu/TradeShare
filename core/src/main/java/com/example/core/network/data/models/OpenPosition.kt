package com.example.core.network.data.models

data class OpenPosition(
    var openpositions: List<Position>

)

data class Position(
    var ID: String,
    var EntryTime: String,
    var TradeType: String,
    var Quantity: String,
    var TakeProfit: Double?,
    var EntryPrice: Double?,
    var StopLoss: Double?
)
