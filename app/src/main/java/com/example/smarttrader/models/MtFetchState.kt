package com.example.smarttrader.models

import com.example.core.network.data.models.MtPosition
import com.example.core.network.data.models.MtPositions

sealed class MtFetchState {
    data class Success(val positions: List<MtPosition>): MtFetchState()
    data class Error(val message: String): MtFetchState()
    object Loading:  MtFetchState()
}
