package com.carlos.data.repositories

import com.carlos.data.mapper.asDomain
import com.carlos.model.DomainAccountMetrics
import com.carlos.model.DomainAccountTrades
import com.carlos.model.DomainClosedTrade
import com.carlos.model.DomainEquityChart
import com.carlos.network.api.MtApi
import com.carlos.network.api.safeApiCall
import com.carlos.network.api.safeFlowApiCall
import com.carlos.network.models.ApiCallResult
import com.carlos.network.models.Broker
import com.carlos.network.models.GraphData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class TradingAccountRepoImpl(
    private val mtApi: MtApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TradingAccountRepo {
    override suspend fun getAccountEquityChart(
        accountId: String,
        startTime: String,
        endTime: String
    ): ApiCallResult<DomainEquityChart> = safeApiCall(dispatcher = dispatcher) {
        return@safeApiCall mtApi.getEquityChart(accountId, startTime, endTime).asDomain()
    }

    override suspend fun searchServer(name: String): ApiCallResult<Broker> =
        safeApiCall(dispatcher = dispatcher) {
            return@safeApiCall mtApi.searchServer(name = name)
        }


    override suspend fun getAccountTrades(accountId: String): ApiCallResult<DomainAccountTrades> =
        safeApiCall(dispatcher) {
            return@safeApiCall mtApi.getAccountTrades(accountId).asDomain()
        }

    override suspend fun getMetrics(accountId: String): ApiCallResult<DomainAccountMetrics<GraphData>> =
        safeApiCall(dispatcher = dispatcher) {
            return@safeApiCall mtApi.getMetrics(accountId).asDomain()
        }

    override suspend fun getMetricsFlow(accountId: String): Flow<ApiCallResult<DomainAccountMetrics<GraphData>>> =
        safeFlowApiCall(dispatcher = dispatcher) {
            return@safeFlowApiCall mtApi.getMetrics(accountId).asDomain()
        }


    override suspend fun getHistoricalTrades(
        accountId: String,
        range: Int,
        offset: Int
    ): ApiCallResult<DomainClosedTrade> = safeApiCall(dispatcher = dispatcher) {
        return@safeApiCall mtApi.getAccountHistoricalTrades(
            accountId = accountId,
            range = range,
            offset = offset
        ).asDomain()
    }


}