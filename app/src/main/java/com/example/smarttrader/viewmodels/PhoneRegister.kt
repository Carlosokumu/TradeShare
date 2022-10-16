package com.example.smarttrader.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.repository.UserRepository
import com.example.smarttrader.models.PhoneRegisterState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhoneRegister(private val userRepository: UserRepository, private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {


    private val _uiState: MutableStateFlow<PhoneRegisterState> = MutableStateFlow(
        PhoneRegisterState.Loading)

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<PhoneRegisterState> = _uiState



    fun uploadPhoneNumber(userName: String,phoneNumber: String){
        viewModelScope.launch(coroutineDispatcher){
            when(val result = userRepository.updatePhoneNumber(userName = userName,phoneNumber = phoneNumber)){
                is ApiCallResult.ApiCallError ->{
                    _uiState.value = PhoneRegisterState.Error("Could not upload your number to the server")
                }
                is ApiCallResult.ServerError ->{
                    _uiState.value =  PhoneRegisterState.Error("Could not upload your number to the server,Please try again later")
                    //Log.d("ERROR", result.errorBody?.message ?: "Server Could not be reached")
                }
                is ApiCallResult.Success ->{
                    _uiState.value = PhoneRegisterState.Success(result.data.response)
                }
            }
        }
    }
}