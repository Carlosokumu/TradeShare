package com.example.core.network.repository

import com.example.core.network.data.models.EquityChart

interface TradingAccountRepo {
   suspend fun getQEquity(accountId: String,startTime: String,endTime: String): EquityChart
}