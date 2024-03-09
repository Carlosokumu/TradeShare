package com.example.core.network.data.models

data class AccountTrades(
    val positions: List<OpenTrade>
)


data class OpenTrade(
    var openPrice: Double,
    var symbol: String,
    var volume: Double,
    var currentPrice: Double,
    var unrealizedProfit: Double

)