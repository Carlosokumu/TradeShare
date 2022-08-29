package com.example.core.network.repository

import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.data.api.MtApi
import com.example.core.network.data.api.TradeApi
import com.example.core.network.data.api.safeApiCall
import com.example.core.network.data.models.MtPositions
import com.example.core.network.data.models.OpenPosition
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class OpenPositionsRepoImp(private val tradeApi: TradeApi,private val mtApi: MtApi,private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : OpenPositionsRepo {



    override suspend fun getOpenPositions(): ApiCallResult<OpenPosition>  = safeApiCall(ioDispatcher){
        return@safeApiCall tradeApi.getAllPositions()
    }

    override suspend fun getMtPositions(): ApiCallResult<MtPositions> = safeApiCall(ioDispatcher){
        return@safeApiCall mtApi.getMtPositions()
    }




}