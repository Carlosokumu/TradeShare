package com.example.core.network.repository

import com.example.core.network.data.models.EquityChart
import com.example.core.network.data.models.Metrics

interface TradingAccountRepo {
   suspend fun getEquity(accountId: String,startTime: String,endTime: String): EquityChart


   suspend fun getMetrics(accountId: String): Metrics
}