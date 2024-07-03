package com.android.swingwizards.models

sealed class HistoricalTradesState() {
    object Loading : HistoricalTradesState()
    data class Trades(val trades: List<HistoryTradeModel>) : HistoricalTradesState()
}