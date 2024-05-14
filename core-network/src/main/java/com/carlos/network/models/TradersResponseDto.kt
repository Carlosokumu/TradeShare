package com.carlos.network.models

import com.google.gson.annotations.SerializedName


data class TradersResponseDto(
    val pagination: Pagination,
    @SerializedName("users")
    val traders: List<UserResponseDto>,
)


data class Pagination(
    @SerializedName("NextPage")
    val next: Int,
    @SerializedName("PreviousPage")
    val previous: Int
)


