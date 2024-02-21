package com.example.core.network.repository

import com.example.core.network.data.api.TradeShareApi
import com.example.core.network.data.models.EquityChart

class EquityChartRepoImpl(private val tradeShareApi: TradeShareApi): EquityChartRepo {
    override suspend fun getQEquity(accountId: String, startTime: String, endTime: String): EquityChart {
        return  tradeShareApi.getEquityChart(accountId, startTime, endTime)
    }
}