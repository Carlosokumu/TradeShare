package com.android.swingwizards.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.swingwizards.data.repository.UserRepo
import com.android.swingwizards.models.InputFieldsStates
import com.android.swingwizards.models.SwitchState
import com.android.swingwizards.models.UiState
import com.carlos.data.repositories.UserRepository
import com.carlos.model.DomainUser
import com.carlos.network.models.ApiCallResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val userRepo: UserRepo,
    private val userRepository: UserRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {


    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Relaxed)

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var _signUpFields = MutableStateFlow(InputFieldsStates())
    val signUpFields get() = _signUpFields.asStateFlow()


    private var _switchState = MutableStateFlow(SwitchState())
    val switchState get() = _switchState.asStateFlow()


    fun onEmailEntered(email: String) {
        _signUpFields.update {
            _signUpFields.value.copy(
                email = email
            )
        }
    }


    fun onUserNameEntered(username: String) {
        _signUpFields.update {
            _signUpFields.value.copy(
                username = username
            )
        }
    }

    fun onPasswordEntered(password: String) {
        _signUpFields.update {
            _signUpFields.value.copy(
                password = password
            )
        }
    }


    fun saveUserUsername(username: String) = viewModelScope.launch {
        userRepo.saveUsername(username)
    }


    fun saveAccessToken(accessToken: String) = viewModelScope.launch {
        userRepo.saveAccessToken(accessToken)
    }

    fun onSwitch(isSwitched: Boolean) {
        _switchState.update {
            _switchState.value.copy(
                isChecked = isSwitched
            )
        }
    }


    fun saveUserLoggedIn() = viewModelScope.launch {
        userRepo.saveUserLoggedInStatus(true)
    }


    fun registerTradeShareUser(username: String, email: String, password: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(coroutineDispatcher) {
            when (val result = userRepository.registerTradeShareUser(
                username = username,
                password = password,
                email = email
            )
            ) {
                is ApiCallResult.ApiCallError -> {
                    _uiState.value =
                        UiState.Error("Something Went Wrong.Try Again Later")
                }

                is ApiCallResult.ServerError -> {
                    Log.d("STATE:", result.errorBody?.error.toString())
                    _uiState.value =
                        UiState.ServerError(
                            code = result.code,
                            message = "Server error"
                        )
                }

                is ApiCallResult.Success -> {
                    Log.d("STATE:", "SUCCESS")
                    _uiState.value = UiState.Success(result.data)
                }

            }
        }
    }


}