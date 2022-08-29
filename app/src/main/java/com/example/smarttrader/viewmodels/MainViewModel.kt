package com.example.smarttrader.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository, private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {



   fun sendConfirmationEmail(email: String,username: String) {
       viewModelScope.launch(coroutineDispatcher) {
           when (val result = userRepository.sendConfirmation(email = email,userName = username)){
               is ApiCallResult.ApiCallError ->{
                   //Schedule Work to send the email when internet comes around
               }
               is ApiCallResult.ServerError ->{
                   //Schedule Work to send the email when internet comes around
               }
               is ApiCallResult.Success ->{
                   //Schedule Work to send the email when internet comes around
               }
           }
       }
   }



}