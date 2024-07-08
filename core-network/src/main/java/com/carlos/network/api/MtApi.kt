package com.carlos.network.api

import com.carlos.network.models.AccountTrades
import com.carlos.network.models.ApiResponse
import com.carlos.network.models.Broker
import com.carlos.network.models.ClosedTrade
import com.carlos.network.models.EquityChart
import com.carlos.network.models.Metrics
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MtApi {
    @FormUrlEncoded
    @POST("trader/register")
    suspend fun connectMetaTraderAccount(
        @Field("name") username: String,
        @Field("password") loginPassword: String,
        @Field("server") server: String,
        @Field("login") login: String,
        @Field("platform") version: String,
        @Field("token") token: String
    ): ApiResponse


    @GET("/equity")
    suspend fun getEquityChart(
        @Query("accountId") accountId: String,
        @Query("startTime") startTime: String,
        @Query("endTime") endTime: String
    ): EquityChart

    @GET("equity_chart")
    suspend fun getMetrics(@Query("account_id") accountId: String): Metrics


    @GET("positions")
    suspend fun getAccountTrades(@Query("account_id") accountId: String): AccountTrades


    @GET("historicaltrades")
    suspend fun getAccountHistoricalTrades(
        @Query("account_id") accountId: String,
        @Query("history_range") range: Int,
        @Query("offset") offset: Int
    ): ClosedTrade


    @GET("servers")
    suspend fun searchServer(@Query("name") name: String): Broker

}