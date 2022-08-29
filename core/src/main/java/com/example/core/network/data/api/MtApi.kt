package com.example.core.network.data.api

import com.example.core.network.data.models.MtPositions
import com.example.core.network.data.models.RegisteredUser
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

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


    @GET("/positions")
    suspend fun getMtPositions(): MtPositions

}