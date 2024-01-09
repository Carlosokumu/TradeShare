package com.example.core.network.repository

import com.example.core.network.data.api.TradeShareApi
import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.data.api.MtApi
import com.example.core.network.data.api.safeApiCall
import com.example.core.network.data.models.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class UserRepositoryImp(private val tradeShareApi: TradeShareApi,
                        private val mtApi: MtApi,
                        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): UserRepository {




    override suspend fun registerUser(
        firstname: String,
        lastname: String,
        email: String,
        userName: String,
        password: String
    ): ApiCallResult<RegisteredUser>  = safeApiCall(ioDispatcher) {
        return@safeApiCall tradeShareApi.registerUser(firstname = firstname, lastname = lastname, username = userName, password = password, email = email)
    }



    override suspend fun registerTradeShareUser(email: String, username: String, password: String): ApiCallResult<SignUpResponse> = safeApiCall(ioDispatcher){
        return@safeApiCall tradeShareApi.registerTradeShareUser(username = username, password = password, email = email)
    }

    override suspend fun updatePhoneNumber(
        userName: String,
        phoneNumber: String
    ): ApiCallResult<PhoneUpdateResponse> =  safeApiCall(ioDispatcher) {
        return@safeApiCall tradeShareApi.updatePhoneNumber(username = userName, phoneNumber = phoneNumber)
    }

    override suspend fun getTradeShareUser(userName: String): ApiCallResult<TradeShareUserResponse> = safeApiCall(ioDispatcher) {
        return@safeApiCall tradeShareApi.getTradeShareUser(userName)
    }

    override suspend fun getTrades(accountId: String,offset: Int,range: Int): ApiCallResult<Trades> = safeApiCall(ioDispatcher) {
        return@safeApiCall mtApi.getTrades(accountId, range, offset)
    }

    override suspend fun loginUser(userName: String, password: String): ApiCallResult<CurrentUserInfo> = safeApiCall(ioDispatcher) {
        return@safeApiCall  tradeShareApi.loginUser(userName,password)
    }

    override suspend fun loginTradeShareUser(
        userName: String,
        password: String
    ): ApiCallResult<LoginResponse> = safeApiCall(ioDispatcher) {
        return@safeApiCall  tradeShareApi.loginTradeShareUser(userName,password)
    }

    override suspend fun connectMetaTraderAccount(
        userName: String,
        metaTraderLogin: String,
        metaTraderPassword: String,
        metaTraderVersion: String,
        server: String
    ): ApiCallResult<MetaAPIResponse> = safeApiCall(ioDispatcher){
        return@safeApiCall mtApi.connectMetaTraderAccount(username = userName, loginPassword =  metaTraderPassword,server = server, login = metaTraderLogin, version =  metaTraderVersion)
    }

    override suspend fun sendCode(email: String): ApiCallResult<OtpCode> = safeApiCall(ioDispatcher){
        return@safeApiCall  tradeShareApi.sendCode(email)
    }

    override suspend fun sendConfirmation(email: String, userName: String): ApiCallResult<Unit> = safeApiCall(ioDispatcher){
        return@safeApiCall  tradeShareApi.sendConfirmation(email,userName)
    }

    override suspend fun getUserInfo(userName: String): ApiCallResult<CurrentUserInfo> = safeApiCall(ioDispatcher){
        return@safeApiCall  tradeShareApi.getUserInfo(userName)
    }




}