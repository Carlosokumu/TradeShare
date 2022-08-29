package com.example.core.network.repository

import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.data.models.*

interface UserRepository {



    suspend fun registerUser(firstname: String,lastname: String,email: String,userName: String,password: String) : ApiCallResult<RegisteredUser>


    suspend fun updatePhoneNumber(userName: String,phoneNumber: String): ApiCallResult<PhoneUpdateResponse>


    suspend fun  loginUser(userName: String,password: String) : ApiCallResult<CurrentUserInfo>


    suspend fun  sendCode(email: String) : ApiCallResult<OtpCode>

    suspend fun sendConfirmation(email: String,userName: String): ApiCallResult<Unit>


    suspend fun getUserInfo(userName: String): ApiCallResult<CurrentUserInfo>
}