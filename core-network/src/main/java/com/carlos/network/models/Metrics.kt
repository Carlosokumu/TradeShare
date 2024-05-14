package com.carlos.network.models

import com.carlos.model.PeriodMetrics

data class Metrics(
    val metrics: Metric
)

data class Metric(
    val bestTrade: Double,
    val wonTradesPercent: Double,
    val lostTradesPercent: Double,
    val deposits: Double,
    val worstTrade: Double,
    val dailyGain: Double,
    val profit: Double,
    val balance: Double,
    val monthlyGain: Double,
    val daysSinceTradingStarted: Double,
    val expectancy: Double,
    val averageWin: Double,
    val averageLoss: Double,
    val dailyGrowth: List<GraphData>,
    val monthlyAnalytics: List<GraphData>,
    val periods: Map<String, PeriodMetrics>

)


//data class PeriodMetrics(val trades: String, val profit: Double?)


data class GraphData(
    val date: String,
    val balance: Double,
    val profit: Double
)
