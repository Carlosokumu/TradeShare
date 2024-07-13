package com.android.swingwizards.models

import com.android.swingwizards.enums.TradeHistoryRange
import com.carlos.model.DomainTraderStats
import com.carlos.model.OpenTrade
import com.carlos.model.Trade


sealed interface TradeHistoryUiState {
    data class State(
        val dataPoints: List<DataPoint>,
        val timeRangeSelected: TradeHistoryRange,
        val openTrades: List<OpenTrade>,
        val historicalTrades: List<Trade>,
        val domainTraderStats: DomainTraderStats
    ) : TradeHistoryUiState


}
