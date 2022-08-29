package com.example.smarttrader.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.repository.UserRepository
import com.example.smarttrader.models.EmailAuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmailAuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<EmailAuthState> = MutableStateFlow(
        EmailAuthState.Loading
    )
    val uiState = _uiState.asStateFlow()



       fun sendEmail(email: String) {
           viewModelScope.launch {
               when (val result = userRepository.sendCode(email)) {
                   is ApiCallResult.ApiCallError -> {
                      _uiState.value = EmailAuthState.Error
                   }
                   is ApiCallResult.ServerError -> {
                       _uiState.value = EmailAuthState.ServerCode(result.code)
                   }
                   is ApiCallResult.Success -> {
                       _uiState.value = EmailAuthState.Success(code = result.data.code)
                   }
               }
           }

       }



}