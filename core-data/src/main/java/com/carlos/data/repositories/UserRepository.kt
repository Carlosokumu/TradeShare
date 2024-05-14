package com.carlos.data.repositories

import com.carlos.core_database.entities.TradingPlatformEntity
import com.carlos.model.DomainTrader
import com.carlos.model.DomainUser
import com.carlos.network.models.ApiCallResult
import com.carlos.network.models.ApiResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun registerTradeShareUser(
        username: String,
        email: String,
        password: String
    ): ApiCallResult<DomainUser>


    suspend fun loginTradeShareUser(username: String, password: String): ApiCallResult<DomainUser>


    suspend fun setPlatformAsSelected(id: Int)
    suspend fun setPlatformAsUnSelected(id: Int)


    suspend fun unselectAllTradingPlatforms()
    fun getSelectedTradingPlatform(): Flow<TradingPlatformEntity?>

    fun getTradingPlatforms(): Flow<List<TradingPlatformEntity>>


    suspend fun getTraders(page: Int): ApiCallResult<List<DomainTrader>>


    suspend fun connectMetaTraderAccount(
        userName: String,
        metaTraderLogin: String,
        metaTraderPassword: String,
        metaTraderVersion: String,
        server: String,
        token: String
    ): ApiCallResult<ApiResponse>

}