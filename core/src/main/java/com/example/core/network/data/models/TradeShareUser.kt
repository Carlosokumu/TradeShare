package com.example.core.network.data.models

import com.google.gson.annotations.SerializedName


data class TradeShareUser(
    @SerializedName("UserName")
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
    val refreshToken: String,
    @SerializedName("AccountId")
    val accountId: String?
)


data class LoginResponse(val user: TradeShareUser)


data class SignUpResponse(val user: TradeShareUser)


data class TradeShareUserResponse(val user: TradeShareUser)