package com.example.smarttrader.models

import com.example.core.network.data.models.RegisteredUser

/*
    *
    * Represents the three states during account creation
    *
 */


sealed class AccountCreationState{

    data class Success(val registeredUser: RegisteredUser): AccountCreationState()
    data class Error(val message: String): AccountCreationState()
    data class ServerError(val code: Int?): AccountCreationState()
    object Loading: AccountCreationState()
    object Relaxed: AccountCreationState()

}
