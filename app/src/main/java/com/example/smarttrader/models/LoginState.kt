package com.example.smarttrader.models

import com.example.core.network.data.models.CurrentUserInfo
import com.example.smarttrader.data.local.entity.User

sealed class LoginState {

    object Relaxed: LoginState()
    data class  Success(val user: User): LoginState()
    object Loading : LoginState()
    data class  Error(val message: String): LoginState()
    data class ServerCode(val code: Int?): LoginState()
}