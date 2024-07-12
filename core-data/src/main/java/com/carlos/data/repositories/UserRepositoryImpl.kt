package com.carlos.data.repositories

import com.carlos.core_database.daos.TradingPlatformDao
import com.carlos.data.mapper.asDomain
import com.carlos.model.DomainTrader
import com.carlos.model.DomainUser
import com.carlos.network.api.MtApi
import com.carlos.network.api.TradeShareApi
import com.carlos.network.api.safeApiCall
import com.carlos.network.api.safeFlowApiCall
import com.carlos.network.models.ApiCallResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val tradeShareApi: TradeShareApi,
    private val mtApi: MtApi,
    private val tradingPlatformDao: TradingPlatformDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {
    override suspend fun registerTradeShareUser(
        username: String, email: String, password: String
    ): ApiCallResult<DomainUser> = safeApiCall(dispatcher) {
        return@safeApiCall tradeShareApi.registerTradeShareUser(
            username = username, password = password, email = email
        ).asDomain()
    }

    override suspend fun loginTradeShareUser(
        username: String, password: String
    ): ApiCallResult<DomainUser> = safeApiCall(dispatcher) {
        return@safeApiCall tradeShareApi.loginTradeShareUser(
            username = username,
            password = password,
        ).asDomain()
    }

    override suspend fun setPlatformAsSelected(id: Int) {
        tradingPlatformDao.markPlatFormAsSelected(id = id)
    }

    override suspend fun setPlatformAsUnSelected(id: Int) {
        tradingPlatformDao.markPlatFormAsUnSelected(id = id)
    }

    override suspend fun unselectAllTradingPlatforms() {
        tradingPlatformDao.unSelectAllPlatforms()
    }

    override fun getSelectedTradingPlatform() = tradingPlatformDao.getSelectedPlatform()


    override fun getTradingPlatforms() = tradingPlatformDao.getTradingPlatforms()


    override suspend fun connectMetaTraderAccount(
        userName: String,
        metaTraderLogin: String,
        metaTraderPassword: String,
        metaTraderVersion: String,
        server: String,
        token: String,
    ) = safeApiCall(dispatcher) {
        return@safeApiCall mtApi.connectMetaTraderAccount(
            username = userName,
            loginPassword = metaTraderPassword,
            server = server,
            login = metaTraderLogin,
            version = metaTraderVersion,
            token = token
        )
    }

    override suspend fun getTraders(page: Int): ApiCallResult<List<DomainTrader>> =
        safeApiCall(dispatcher) {
            return@safeApiCall tradeShareApi.getTraders(page).traders.asDomain()
        }

    override suspend fun getTradersFlow(page: Int): Flow<ApiCallResult<List<DomainTrader>>> =
        safeFlowApiCall(dispatcher) {
            return@safeFlowApiCall tradeShareApi.getTraders(page = page).traders.asDomain()
        }


}