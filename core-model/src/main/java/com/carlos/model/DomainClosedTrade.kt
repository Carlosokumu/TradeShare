package com.carlos.model

data class DomainClosedTrade(val trades: List<Trade>)
data class Trade(
    val profit: String,
    val type: String,
    val volume: String,
    val symbol: String,
    val createdAt: String
)
