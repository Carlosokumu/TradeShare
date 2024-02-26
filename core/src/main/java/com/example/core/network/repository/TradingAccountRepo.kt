package com.example.core.network.repository

import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.data.models.AccountTrades
import com.example.core.network.data.models.EquityChart
import com.example.core.network.data.models.Metrics

interface TradingAccountRepo {
   suspend fun getEquity(accountId: String,startTime: String,endTime: String): ApiCallResult<EquityChart>


   suspend fun getAccountTrades(accountId: String): ApiCallResult<AccountTrades>

   suspend fun getMetrics(accountId: String): ApiCallResult<Metrics>
}