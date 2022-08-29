package com.example.core.network.data.models

import com.google.gson.annotations.SerializedName


data class CurrentUserInfo(
   val user: User
)

data class User(
    @SerializedName("FirstName")
    var firstname: String,
    @SerializedName("LastName")
    var lastname: String,
    @SerializedName("Email")
    var email: String,
    @SerializedName("Username")
    var username: String,
    @SerializedName("FloatingProfit")
    var floatingprofit: Double = 0.0,
    @SerializedName("Equity")
    var equity: Double = 0.0,
    @SerializedName("Balance")
    var balance: Double = 0.0,
    @SerializedName("Password")
    var password: String,
    @SerializedName("PhoneNumber")
    var phonenumber: String?,
    @SerializedName("PercentageContribution")
    var contribution: Double
)
