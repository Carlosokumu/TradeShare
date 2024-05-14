package com.carlos.network.models

import com.google.gson.annotations.SerializedName

data class SignUpResponseDto(val username: String, val email: String)


data class UserResponseDto(
    @SerializedName("UserName")
    val username: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("TradingAccounts")
    val tradingAccounts: List<TradingAccounts>?
)


data class TradingAccounts(
    @SerializedName("Platform") val platform: String,
    @SerializedName("AccountId") val accountId: String
)