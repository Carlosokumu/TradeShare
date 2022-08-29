package com.example.core.network.repository

import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.data.models.MtPositions
import com.example.core.network.data.models.OpenPosition
import com.example.core.network.data.models.RegisteredUser

interface OpenPositionsRepo {

    /**
       Get All positions from cTrader Api
     */

    suspend fun  getOpenPositions(): ApiCallResult<OpenPosition>


    /**

      Get All Mt Positions
     */
    suspend fun  getMtPositions(): ApiCallResult<MtPositions>

}