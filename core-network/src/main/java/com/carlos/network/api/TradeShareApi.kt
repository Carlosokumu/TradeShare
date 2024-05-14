package com.carlos.network.api


import com.carlos.network.models.ApiResponse
import com.carlos.network.models.TradersResponseDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Query

interface TradeShareApi {
    @FormUrlEncoded
    @POST("auth/user/register")
    suspend fun registerTradeShareUser(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse


    @FormUrlEncoded
    @POST("auth/user/login")
    suspend fun loginTradeShareUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse


    @GET("api/v1/user/traders")
    suspend fun getTraders(@Query("page") page: Int): TradersResponseDto


}