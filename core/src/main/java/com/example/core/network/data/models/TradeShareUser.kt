package com.example.core.network.data.models

import com.google.gson.annotations.SerializedName


data class TradeShareUser(
    @SerializedName("Username")
    val username: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("Avatar")
    val avatar: String,
    @SerializedName("TradingAccount")
    val tradingAccount: Any?,
    @SerializedName("AccessToken")
    val accessToken: String,
    @SerializedName("RefreshToken")
    val refreshToken: String
)