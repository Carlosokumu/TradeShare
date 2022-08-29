package com.example.smarttrader.models


sealed class PhoneRegisterState {

    data class Success(val response: String): PhoneRegisterState()
    data class Error(val message: String): PhoneRegisterState()
    object Loading: PhoneRegisterState()
}