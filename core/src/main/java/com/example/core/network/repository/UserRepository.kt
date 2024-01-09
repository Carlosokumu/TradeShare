package com.example.core.network.repository

import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.data.models.*

interface UserRepository {



    suspend fun registerUser(firstname: String,lastname: String,email: String,userName: String,password: String) : ApiCallResult<RegisteredUser>


    suspend fun  registerTradeShareUser(email: String,username: String,password: String): ApiCallResult<SignUpResponse>


    suspend fun updatePhoneNumber(userName: String,phoneNumber: String): ApiCallResult<PhoneUpdateResponse>



    suspend fun getTrades(accountId: String,offset: Int,range: Int): ApiCallResult<Trades>


    suspend fun  loginUser(userName: String,password: String) : ApiCallResult<CurrentUserInfo>


    suspend fun loginTradeShareUser(userName: String,password: String): ApiCallResult<LoginResponse>


    suspend fun  connectMetaTraderAccount(userName: String,metaTraderLogin: String,metaTraderPassword: String,metaTraderVersion: String,server: String): ApiCallResult<MetaAPIResponse>


    suspend fun  sendCode(email: String) : ApiCallResult<OtpCode>

    suspend fun sendConfirmation(email: String,userName: String): ApiCallResult<Unit>


    suspend fun getUserInfo(userName: String): ApiCallResult<CurrentUserInfo>
}