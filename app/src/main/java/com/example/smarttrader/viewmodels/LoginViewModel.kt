package com.example.smarttrader.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.repository.UserRepository
import com.example.smarttrader.data.local.mapper.toUser
import com.example.smarttrader.models.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {


    private val _uiState: MutableStateFlow<LoginState> = MutableStateFlow(
        LoginState.Relaxed
    )
    val uiState = _uiState.asStateFlow()



    fun loginUser(username: String, password: String) = viewModelScope.launch {
        Log.d("INIT", "initialized")
        _uiState.value = LoginState.Loading

        when (val result = userRepository.loginUser(userName = username, password = password)) {
            is ApiCallResult.ApiCallError -> {
                _uiState.value = LoginState.Error(message = "Something went wrong")
                Log.d("APICALLERR", "Something went wrong")
            }
            is ApiCallResult.ServerError -> {
                _uiState.value = LoginState.ServerCode(result.code)
            }
            is ApiCallResult.Success -> {
                _uiState.value = LoginState.Success(user = result.data.toUser())
               // _onServerResponse.postValue(LoginState.Success(response = result.data.response))
                Log.d("APICALLERR", result.data.toString())
            }
        }
    }
}