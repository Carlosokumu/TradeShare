package com.example.core.network.data.models

import com.google.gson.annotations.SerializedName

data class RegisteredUser(
    val user: UserInfo

)


data class UserInfo(
    @SerializedName("FirstName")
    var firstname: String,
    @SerializedName("LastName")
    var lastname: String,
    @SerializedName("Email")
    var email: String,
    @SerializedName("Username")
    var username: String,
    @SerializedName("Password")
    var password: String,
)