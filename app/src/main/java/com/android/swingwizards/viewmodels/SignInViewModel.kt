package com.android.swingwizards.viewmodels

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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(private val userRepository: UserRepository,
                      private val userRepo: UserRepo,
                      private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO): ViewModel() {


    private var _signInFields = MutableStateFlow(InputFieldsStates())
    val signInFields get() = _signInFields.asStateFlow()

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Relaxed)

    val uiState: StateFlow<UiState> = _uiState



    fun saveUserLoggedIn() = viewModelScope.launch {
        userRepo.saveUserLoggedInStatus(true)
    }



    fun saveUserUsername(username: String) = viewModelScope.launch {
        userRepo.saveUsername(username)
    }






    fun onUserNameEntered(username: String) {
        _signInFields.update {
            _signInFields.value.copy(
                username = username
            )
        }
    }
    fun onPasswordEntered(password: String) {
        _signInFields.update {
            _signInFields.value.copy(
                password = password
            )
        }
    }

    fun signInUser(username: String,password: String){
        _uiState.value = UiState.Loading
        viewModelScope.launch(coroutineDispatcher) {
            when (val result = userRepository.loginTradeShareUser(
                userName = username,
                password = password
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
                            message = "Server error"
                        )
                }
                is ApiCallResult.Success -> {
                    _uiState.value = UiState.Success(result.data)
                }

            }
        }
    }
}