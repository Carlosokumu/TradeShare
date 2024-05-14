package com.carlos.network.models

import com.google.gson.annotations.SerializedName


data class ErrorResponse(
    @SerializedName("error")
    val error: String
)
