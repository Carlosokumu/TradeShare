package com.example.smarttrader.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.repository.UserRepository
import com.example.smarttrader.data.local.entity.User
import com.example.smarttrader.data.local.mapper.toUser
import com.example.smarttrader.data.repository.UserRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val userRepo: UserRepo,private  val userRepository: UserRepository,private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {


    private val _userLive: MutableLiveData<User> = MutableLiveData()
    val user = _userLive

    fun getUser(username: String) = viewModelScope.launch {
        val user = userRepo.getUserByUsername(username)
        _userLive.postValue(user)
    }

    fun insertUser(user: User) = viewModelScope.launch {
        userRepo.saveUser(user)
    }

    fun fetchUserInfo(username: String){
        viewModelScope.launch(coroutineDispatcher) {
            when (val result = userRepository.getUserInfo(username)){
                is ApiCallResult.ApiCallError ->{

                }
                is ApiCallResult.ServerError ->{

                }
                is ApiCallResult.Success ->{
                    insertUser(result.data.toUser())

                }
            }
        }
    }


}