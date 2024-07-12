package com.carlos.model

data class DomainAccountMetrics<T>(
    val dailyGrowth: List<T>,
    val bestTrade: Double,
    val bestTradesPips: Double,
    val worstTradePips: Double,
    val wonTradesPercent: Double,
    val lostTradesPercent: Double,
    val worstTrade: Double,
    val dailyGain: Double,
    val profit: Double,
    val deposits: Double,
    val risk: Double,
    val balance: Double,
    val monthlyGain: Double,
    val daysSinceTradingStarted: Double,
    val expectancy: Double,
    val averageWin: Double,
    val averageLoss: Double,
    val monthlyAnalytics: List<T>,
    val periods: Map<String, PeriodMetrics>
)


data class PeriodMetrics(val trades: String, val profit: Double?)