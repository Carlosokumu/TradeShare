package com.example.core.network.repository

import com.example.core.network.data.api.TradeApi
import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.data.api.MtApi
import com.example.core.network.data.api.safeApiCall
import com.example.core.network.data.models.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class UserRepositoryImp(private val tradeApi: TradeApi,
                        private val mtApi: MtApi,
                        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): UserRepository {




    override suspend fun registerUser(
        firstname: String,
        lastname: String,
        email: String,
        userName: String,
        password: String
    ): ApiCallResult<RegisteredUser>  = safeApiCall(ioDispatcher) {
        return@safeApiCall tradeApi.registerUser(firstname = firstname, lastname = lastname, username = userName, password = password, email = email)
    }



    override suspend fun registerTradeShareUser(email: String, username: String, password: String): ApiCallResult<TradeShareUser> = safeApiCall(ioDispatcher){
        return@safeApiCall tradeApi.registerTradeShareUser(username = username, password = password, email = email)
    }

    override suspend fun updatePhoneNumber(
        userName: String,
        phoneNumber: String
    ): ApiCallResult<PhoneUpdateResponse> =  safeApiCall(ioDispatcher) {
        return@safeApiCall tradeApi.updatePhoneNumber(username = userName, phoneNumber = phoneNumber)
    }

    override suspend fun loginUser(userName: String, password: String): ApiCallResult<CurrentUserInfo> = safeApiCall(ioDispatcher) {
        return@safeApiCall  tradeApi.loginUser(userName,password)
    }

    override suspend fun loginTradeShareUser(
        userName: String,
        password: String
    ): ApiCallResult<TradeShareUser> = safeApiCall(ioDispatcher) {
        return@safeApiCall  tradeApi.loginTradeShareUser(userName,password)
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
        return@safeApiCall  tradeApi.sendCode(email)
    }

    override suspend fun sendConfirmation(email: String, userName: String): ApiCallResult<Unit> = safeApiCall(ioDispatcher){
        return@safeApiCall  tradeApi.sendConfirmation(email,userName)
    }

    override suspend fun getUserInfo(userName: String): ApiCallResult<CurrentUserInfo> = safeApiCall(ioDispatcher){
        return@safeApiCall  tradeApi.getUserInfo(userName)
    }




}