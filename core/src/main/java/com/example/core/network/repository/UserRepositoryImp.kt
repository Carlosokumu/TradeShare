package com.example.core.network.repository

import com.example.core.network.data.api.TradeApi
import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.data.api.safeApiCall
import com.example.core.network.data.models.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class UserRepositoryImp(private val tradeApi: TradeApi,
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

    override suspend fun updatePhoneNumber(
        userName: String,
        phoneNumber: String
    ): ApiCallResult<PhoneUpdateResponse> =  safeApiCall(ioDispatcher) {
        return@safeApiCall tradeApi.updatePhoneNumber(username = userName, phoneNumber = phoneNumber)
    }

    override suspend fun loginUser(userName: String, password: String): ApiCallResult<CurrentUserInfo> = safeApiCall(ioDispatcher) {
        return@safeApiCall  tradeApi.loginUser(userName,password)
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