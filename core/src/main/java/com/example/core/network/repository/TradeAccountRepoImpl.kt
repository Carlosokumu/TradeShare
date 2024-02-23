package com.example.core.network.repository

import com.example.core.network.data.api.TradeShareApi
import com.example.core.network.data.models.EquityChart
import com.example.core.network.data.models.Metrics

class TradeAccountRepoImpl(private val tradeShareApi: TradeShareApi) : TradingAccountRepo {
    override suspend fun getEquity(
        accountId: String,
        startTime: String,
        endTime: String
    ): EquityChart {
        return tradeShareApi.getEquityChart(accountId, startTime, endTime)
    }


    override suspend fun getMetrics(accountId: String): Metrics {
        return tradeShareApi.getMetrics(accountId)
    }
}

