package com.android.swingwizards.viewmodels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.swingwizards.data.repository.UserRepo
import com.android.swingwizards.models.InputFieldsStates
import com.android.swingwizards.models.UiState
import com.carlos.data.repositories.TradingAccountRepo
import com.carlos.data.repositories.UserRepository
import com.carlos.network.models.ApiCallResult
import com.carlos.network.models.Server
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MetaTraderViewModel(
    private val userRepository: UserRepository,
    private val userRepo: UserRepo,
    private val tradingAccountRepo: TradingAccountRepo,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var _metaTraderFields = MutableStateFlow(InputFieldsStates())
    val metaTraderFields get() = _metaTraderFields.asStateFlow()

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Relaxed)

    val uiState: StateFlow<UiState> = _uiState

    private val _accessToken = MutableLiveData<String>()
    val accessToken = _accessToken


    private val _brokerServers: MutableStateFlow<List<Server>> = MutableStateFlow(emptyList())
    val brokerServers = _brokerServers


    fun onLoginEntered(login: String) {
        _metaTraderFields.update {
            _metaTraderFields.value.copy(
                metaTraderLogin = login
            )
        }
    }


    init {
        getAccessToken()
    }


    fun saveUserAccountId(accountId: String) = viewModelScope.launch {
        userRepo.saveUserAccountId(accountId = accountId)
    }


    fun connectTradingAccount(
        metaTraderPassword: String,
        metaTraderLogin: String,
        server: String,
        metaTraderVersion: String,
        token: String
    ) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(coroutineDispatcher) {
            userRepo.getUsername.collectLatest { username ->
                if (username != null) {
                    when (val result = userRepository.connectMetaTraderAccount(
                        userName = username,
                        metaTraderLogin = metaTraderLogin,
                        metaTraderPassword = metaTraderPassword,
                        metaTraderVersion = metaTraderVersion,
                        server = server,
                        token = token
                    )) {
                        is ApiCallResult.ApiCallError -> {
                            _uiState.value = UiState.Error("Something Went Wrong.Try Again Later")
                        }

                        is ApiCallResult.ServerError -> {
                            _uiState.value = UiState.ServerError(
                                code = result.code,
                                message = result.errorBody?.error ?: "Server Error"
                            )
                        }

                        is ApiCallResult.Success -> {
                            _uiState.value = UiState.Success(result.data)
                        }

                    }
                }
            }
        }

    }


    private fun getAccessToken() {
        viewModelScope.launch {
            userRepo.getAccessToken.collectLatest { accessToken ->
                _accessToken.value = accessToken
            }
        }
    }


    fun searchServer(name: String) = viewModelScope.launch {
        when (val result = tradingAccountRepo.searchServer(name = name)) {
            is ApiCallResult.Success -> {
                val searchResult = result.data
                _brokerServers.value = searchResult.servers
            }

            is ApiCallResult.ApiCallError -> {

            }

            is ApiCallResult.ServerError -> {

            }
        }
    }


    fun onPasswordEntered(metaTraderPassword: String) {
        _metaTraderFields.update {
            _metaTraderFields.value.copy(
                metaTraderPassword = metaTraderPassword
            )
        }
    }
}