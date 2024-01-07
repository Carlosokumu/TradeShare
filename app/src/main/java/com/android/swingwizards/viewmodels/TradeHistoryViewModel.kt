package com.android.swingwizards.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.swingwizards.data.repository.UserRepo
import com.android.swingwizards.models.UiState
import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.data.models.Trade
import com.example.core.network.repository.UserRepository
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
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Relaxed)

    val uiState: StateFlow<UiState> = _uiState


    private val _trades: MutableStateFlow<List<Trade>> = MutableStateFlow(listOf())
    val trade = _trades.asStateFlow()


    private val _userUserName = MutableLiveData<String>()
    val username = _userUserName


    private val _userAccountId = MutableLiveData<String>()
    val userAccountId = _userAccountId



    init {
        getAccountId()
    }


    fun setTrades(trades: List<Trade>) {
        _trades.value = trades
    }



    fun getUsername() {
        viewModelScope.launch {
            userRepo.getUsername.collectLatest { username ->
                _userUserName.value = username
            }
        }
    }


    private fun getAccountId() {
        viewModelScope.launch {
            userRepo.getAccountId.collectLatest { accountId ->
                _userAccountId.value = accountId
            }
        }
    }



    fun fetchTrades(offset: Int, accountId: String, range: Int) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(coroutineDispatcher) {
            when (val result = userRepository.getTrades(accountId, offset, range)
            ) {
                is ApiCallResult.ApiCallError -> {
                    _uiState.value =
                        UiState.Error("Something Went Wrong.Try Again Later")
                }

                is ApiCallResult.ServerError -> {
                    _uiState.value =
                        UiState.ServerError(
                            code = result.code,
                            message = "Server error"
                        )
                }

                is ApiCallResult.Success -> {
                    _uiState.value = UiState.Success(result.data.trades)
                }

                else -> {}
            }
        }
    }
}