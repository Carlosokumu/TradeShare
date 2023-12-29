package com.example.core.network.data.models

import com.google.gson.annotations.SerializedName

data class MetaAPIResponse(
    @SerializedName("deployed_account")
    val deployedAccount: DeployedAccount) {
}


data class DeployedAccount(
    @SerializedName("_id")
    val accountId: String)