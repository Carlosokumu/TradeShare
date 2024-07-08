package com.carlos.data.repositories

import com.carlos.model.DomainAccountMetrics
import com.carlos.model.DomainAccountTrades
import com.carlos.model.DomainClosedTrade
import com.carlos.model.DomainEquityChart
import com.carlos.network.models.ApiCallResult
import com.carlos.network.models.Broker
import com.carlos.network.models.GraphData


interface TradingAccountRepo {
    suspend fun getAccountEquityChart(
        accountId: String, startTime: String, endTime: String
    ): ApiCallResult<DomainEquityChart>


    suspend fun searchServer(name: String): ApiCallResult<Broker>


    suspend fun getAccountTrades(accountId: String): ApiCallResult<DomainAccountTrades>

    suspend fun getMetrics(accountId: String): ApiCallResult<DomainAccountMetrics<GraphData>>


    suspend fun getHistoricalTrades(
        accountId: String, range: Int, offset: Int
    ): ApiCallResult<DomainClosedTrade>


}
