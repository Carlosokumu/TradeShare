package com.example.smarttrader.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.repository.UserRepository
import com.example.smarttrader.data.local.entity.User
import com.example.smarttrader.data.repository.UserRepo
import com.example.smarttrader.models.AccountCreationState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val userRepo: UserRepo,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState: MutableStateFlow<AccountCreationState> =
        MutableStateFlow(AccountCreationState.Relaxed)

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<AccountCreationState> = _uiState


    fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        userName: String
    ) {
        _uiState.value = AccountCreationState.Loading
        viewModelScope.launch(coroutineDispatcher) {
            when (val result = userRepository.registerUser(
                firstname = firstName,
                lastname = lastName,
                email = email,
                password = password,
                userName = userName
            )) {
                is ApiCallResult.ApiCallError -> {
                    _uiState.value =
                        AccountCreationState.Error("Something Went Wrong.Try Again Later")
                    Log.d("ERROR", "Connectivity Error")
                }
                is ApiCallResult.ServerError -> {
                    _uiState.value =
                        AccountCreationState.ServerError(result.code)
                    Log.d("ERROR", result.errorBody?.message ?: "Server Could not be reached")
                }
                is ApiCallResult.Success -> {
                    _uiState.value = AccountCreationState.Success(result.data)
                }

            }
        }
    }


    fun saveUser(user: User) = viewModelScope.launch {
        userRepo.saveUser(user)
    }


}