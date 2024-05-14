package com.android.swingwizards.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.swingwizards.models.InputFieldsStates
import com.android.swingwizards.models.UiState
import com.android.swingwizards.roundOffDecimal
import com.carlos.data.repositories.TradingAccountRepo
import com.carlos.data.repositories.UserRepository
import com.carlos.network.models.ApiCallResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TradersViewModel(
    private val userRepository: UserRepository,
    private val tradingAccountRepo: TradingAccountRepo,
) : ViewModel() {


    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState> = _uiState


    init {
        getTraders(page = 0)
    }

    private fun getTraders(page: Int) {
        viewModelScope.launch {
            when (val response = userRepository.getTraders(page = page)) {
                is ApiCallResult.ApiCallError -> {
                    _uiState.value =
                        UiState.Error("Something Went Wrong.Please check your internet connection and try again")
                }

                is ApiCallResult.ServerError -> {
                    _uiState.value = UiState.ServerError(
                        code = response.code,
                        message = response.errorBody?.error ?: "cannot connect to server now"
                    )
                }

                is ApiCallResult.Success -> {
                    val traders = response.data
                    for (trader in traders) {
                        fetchTraderStats(
                            trader.accountId.toString(),
                            responseCallback = { additionalInfo ->
                                traders[traders.indexOf(trader)].roI = roundOffDecimal(
                                    additionalInfo.roi
                                ).toString()
                            })
                    }
                    _uiState.value = UiState.Success(traders)

                }
            }
        }
    }


    private fun fetchTraderStats(accountId: String, responseCallback: (AdditionalInfo) -> Unit) {
        viewModelScope.launch {
            when (val response = tradingAccountRepo.getMetrics(accountId = accountId)) {
                is ApiCallResult.ApiCallError -> {
                    _uiState.value =
                        UiState.Error("Something Went Wrong.Please check your internet connection and try again")
                }

                is ApiCallResult.ServerError -> {
                    _uiState.value = UiState.ServerError(
                        code = response.code,
                        message = response.errorBody?.error ?: "cannot connect to server now"
                    )
                }

                is ApiCallResult.Success -> {
                    val metrics = response.data
                    responseCallback(
                        AdditionalInfo(
                            risk = metrics.expectancy, roi = calculateROI(
                                metrics.profit, metrics.deposits
                            )
                        )
                    )
                }
            }
        }
    }
}


fun calculateROI(profit: Double, initialInvestment: Double): Double {
    return profit / initialInvestment * 100
}


data class AdditionalInfo(
    val roi: Double, val risk: Double
)