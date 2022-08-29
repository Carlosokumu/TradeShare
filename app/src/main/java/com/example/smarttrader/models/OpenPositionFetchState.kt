package com.example.smarttrader.models

import com.example.core.network.data.models.OpenPosition


sealed class OpenPositionFetchState  {

    data class Success(val positions: OpenPosition): OpenPositionFetchState()
    data class Error(val message: String): OpenPositionFetchState()
    object Loading: OpenPositionFetchState()
}
