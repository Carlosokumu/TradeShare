package com.example.core.network.data.api

import com.example.core.network.data.models.MetaAPIResponse
import com.example.core.network.data.models.MtPositions
import com.example.core.network.data.models.RegisteredUser
import com.example.core.network.data.models.TradeShareUser
import com.example.core.network.data.models.Trades
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface  MtApi {

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
    @POST("/register")
    suspend fun  connectMetaTraderAccount( @Field("name") username: String,@Field("password") loginPassword: String, @Field("server") server: String,@Field("login") login: String,@Field("platform") version: String): MetaAPIResponse



    suspend fun  getTrades(@Query("account_id") accountId: String,@Query("history_range") range: Int,@Query("offset") offset: Int): Trades

    @GET("/positions")
    suspend fun getMtPositions(): MtPositions

}