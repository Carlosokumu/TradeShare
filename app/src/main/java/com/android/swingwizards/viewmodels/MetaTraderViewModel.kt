package com.android.swingwizards.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.swingwizards.data.repository.UserRepo
import com.android.swingwizards.models.InputFieldsStates
import com.android.swingwizards.models.UiState
import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.repository.UserRepository
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
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var _metaTraderFields = MutableStateFlow(InputFieldsStates())
    val metaTraderFields get() = _metaTraderFields.asStateFlow()

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Relaxed)

    val uiState: StateFlow<UiState> = _uiState


    fun onLoginEntered(login: String) {
        _metaTraderFields.update {
            _metaTraderFields.value.copy(
                metaTraderLogin = login
            )
        }
    }


    fun saveUserAccountId(accountId: String) = viewModelScope.launch {
        userRepo.saveUserAccountId(accountId = accountId)
    }


    fun subMitDetails(
        metaTraderPassword: String,
        metaTraderLogin: String,
        server: String,
        metaTraderVersion: String
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
                        server = server
                    )
                    ) {
                        is ApiCallResult.ApiCallError -> {
                            _uiState.value =
                                UiState.Error("Something Went Wrong.Try Again Later")
                        }

                        is ApiCallResult.ServerError -> {
                            _uiState.value =
                                UiState.ServerError(
                                    code = result.code,
                                    message = result.errorBody?.message ?: "Server Error"
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


    fun onPasswordEntered(metaTraderPassword: String) {
        _metaTraderFields.update {
            _metaTraderFields.value.copy(
                metaTraderPassword = metaTraderPassword
            )
        }
    }
}