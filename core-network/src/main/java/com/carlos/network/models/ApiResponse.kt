package com.carlos.network.models

import com.carlos.model.OpenTrade
import com.carlos.model.Trade
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val signUpResponseDto: SignUpResponseDto,
    val token: String,
    @SerializedName("user")
    val userResponseDto: UserResponseDto,
    val success: Boolean?,
    val data: ApiData?
)


data class ApiData(
    val error: String?,
    val deployedAccount: DeployedAccount
)


data class DeployedAccount(
    @SerializedName("_id")
    val accountId: String
)


data class AccountTrades(
    val positions: List<OpenTrade>
)

data class ClosedTrade(val trades: List<Trade>)


