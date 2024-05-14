package com.android.swingwizards.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.swingwizards.data.repository.UserRepo
import com.android.swingwizards.enums.PeriodRange
import com.android.swingwizards.enums.TimeRange
import com.android.swingwizards.enums.TradeHistoryRange
import com.android.swingwizards.models.TradeHistoryUiState
import com.android.swingwizards.models.UiState
import com.android.swingwizards.utils.toDataPoint
import com.carlos.data.repositories.TradingAccountRepo
import com.carlos.data.repositories.UserRepository
import com.carlos.model.DomainAccountMetrics
import com.carlos.network.models.ApiCallResult
import com.carlos.network.models.GraphData
import com.example.core.network.data.models.Trade
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TradeHistoryViewModel(
    private val userRepository: UserRepository,
    private val userRepo: UserRepo,
    private val tradingAccountRepo: TradingAccountRepo,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Relaxed)
    val uiState: StateFlow<UiState> = _uiState


    var tradeHistoryUiState by mutableStateOf(
        TradeHistoryUiState.State(
            dataPoints = emptyList(),
            timeRangeSelected = TradeHistoryRange.values().first(),
            openTrades = emptyList(),
            historicalTrades = emptyList()
        )
    )

    private val _metrics = MutableStateFlow(
        DomainAccountMetrics<GraphData>(
            dailyGrowth = emptyList(),
            bestTrade = 0.0,
            wonTradesPercent = 0.0,
            lostTradesPercent = 0.0,
            worstTrade = 0.0,
            dailyGain = 0.0,
            balance = 0.0,
            monthlyGain = 0.0,
            daysSinceTradingStarted = 0.0,
            expectancy = 0.0,
            averageWin = 0.0,
            averageLoss = 0.0,
            monthlyAnalytics = emptyList(),
            periods = hashMapOf(),
            risk = 0.0,
            profit = 0.0,
            deposits = 0.0

        )
    )
    val metrics = _metrics.asStateFlow()
    private val _trades: MutableStateFlow<List<Trade>> = MutableStateFlow(listOf())
    val trade = _trades.asStateFlow()


    private val _userUserName = MutableLiveData<String>()
    val username = _userUserName


    init {
        getUsername()
    }


    private fun getUsername() {
        viewModelScope.launch {
            userRepo.getUsername.collectLatest { username ->
                _userUserName.value = username
            }
        }
    }


    fun getMetrics(accountId: String, timeRange: TimeRange) = viewModelScope.launch {
        when (val response = tradingAccountRepo.getMetrics(accountId = accountId)) {
            is ApiCallResult.ApiCallError -> {
                //Handle Errors
            }

            is ApiCallResult.Success -> {
                _metrics.value = response.data
                tradeHistoryUiState = tradeHistoryUiState.copy(
                    dataPoints = response.data.toDataPoint(timeRange)
                )

            }

            is ApiCallResult.ServerError -> {

            }

            else -> {}
        }
    }


    fun getAccountTrades(accountId: String) {
        viewModelScope.launch {
            when (val response = tradingAccountRepo.getAccountTrades(accountId)) {
                is ApiCallResult.ApiCallError -> {
                    //Handle Errors to UI
                }

                is ApiCallResult.Success -> {
                    Log.d("OPENPOSITIONSUC:", response.data.toString())
                    tradeHistoryUiState = tradeHistoryUiState.copy(
                        openTrades = response.data.positions
                    )
                }

                is ApiCallResult.ServerError -> {
                    Log.d("OPENPOSITIONSER:", response.errorBody?.error.toString())
                }
            }
        }
    }


    fun fetchHistoricalTrades(offset: Int, accountId: String, range: Int) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(coroutineDispatcher) {
            when (val result = tradingAccountRepo.getHistoricalTrades(accountId, range, offset)) {
                is ApiCallResult.ApiCallError -> {
                    _uiState.value = UiState.Error("Something Went Wrong.Try Again Later")
                }

                is ApiCallResult.ServerError -> {
                    _uiState.value = UiState.ServerError(
                        code = result.code, message = "Server error"
                    )
                }

                is ApiCallResult.Success -> {
                    tradeHistoryUiState = tradeHistoryUiState.copy(
                        historicalTrades = result.data.trades
                    )
                    _uiState.value = UiState.Success(result.data.trades)
                }

                else -> {}
            }
        }
    }

    fun onTimeRangeSelected(timeRange: TradeHistoryRange, accountId: String) {
        if (timeRange != tradeHistoryUiState.timeRangeSelected) {
            tradeHistoryUiState = tradeHistoryUiState.copy(
                timeRangeSelected = timeRange
            )
            getMetrics(accountId, timeRange = timeRange.timeRange)
        }
    }


    fun onPeriodSelected(periodRange: PeriodRange, accountId: String) {
        when (periodRange) {
            PeriodRange.Day -> {
                fetchHistoricalTrades(
                    offset = 0, accountId = accountId, range = 1
                )
            }

            PeriodRange.Week -> {
                fetchHistoricalTrades(
                    offset = 0, accountId = accountId, range = 2
                )
            }

            PeriodRange.Month -> {
                fetchHistoricalTrades(
                    offset = 0, accountId = accountId, range = 3
                )
            }
        }
    }
}