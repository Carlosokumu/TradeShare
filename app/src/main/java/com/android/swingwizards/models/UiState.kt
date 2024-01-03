package com.android.swingwizards.models

import com.example.core.network.data.models.TradeShareUser

sealed class UiState {
    data class Success<T>(val response: T) : UiState()
    data class Error(val message: String) : UiState()
    data class ServerError(val code: Int?, val message: String) : UiState()
    object Loading : UiState()
    object  Relaxed: UiState()
}