package com.example.core.network.data.api

import com.example.core.network.data.models.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface TradeApi {

    @FormUrlEncoded
    @POST("/tradex/user/register")
    suspend fun registerUser(
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String,
    ): RegisteredUser


    @FormUrlEncoded
    @POST("/tradex/user/register")
    suspend fun registerTradeShareUser(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): SignUpResponse

    @FormUrlEncoded
    @PATCH("/tradex/user/phonenumber")
    suspend fun updatePhoneNumber(
        @Field("username") username: String,
        @Field("phonenumber") phoneNumber: String,
    ): PhoneUpdateResponse


    @GET("/tradex/positions/all")
    suspend fun getAllPositions(): OpenPosition

    @FormUrlEncoded
    @POST("/tradex/user/login")
    suspend fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): CurrentUserInfo



    @FormUrlEncoded
    @POST("/tradex/user/login")
    suspend fun loginTradeShareUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse




    @FormUrlEncoded
    @POST("/tradex/user/email")
    suspend fun sendCode(@Field("email") email: String): OtpCode

    @FormUrlEncoded
    @POST("/tradex/user/confirmation")
    suspend fun sendConfirmation(@Field("email") email: String, @Field("username") username: String)


    @GET("/tradex/user/userinfo")
    suspend fun getUserInfo(@Query("username") username: String): CurrentUserInfo

}