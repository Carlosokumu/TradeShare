package com.example.core.network.repository

import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.data.api.MtApi
import com.example.core.network.data.api.TradeShareApi
import com.example.core.network.data.api.safeApiCall
import com.example.core.network.data.models.AccountTrades
import com.example.core.network.data.models.EquityChart
import com.example.core.network.data.models.Metrics
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TradeAccountRepoImpl(private val mtApi: MtApi,private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : TradingAccountRepo {
    override suspend fun getEquity(
        accountId: String,
        startTime: String,
        endTime: String
    ): ApiCallResult<EquityChart> = safeApiCall(ioDispatcher) {
        return@safeApiCall mtApi.getEquityChart(accountId, startTime, endTime)
    }

    override suspend fun getAccountTrades(accountId: String): ApiCallResult<AccountTrades> = safeApiCall(dispatcher = ioDispatcher) {
        return@safeApiCall mtApi.getAccountTrades(accountId)
    }


    override suspend fun getMetrics(accountId: String): ApiCallResult<Metrics>  = safeApiCall(ioDispatcher){
        return@safeApiCall mtApi.getMetrics(accountId)
    }
}

