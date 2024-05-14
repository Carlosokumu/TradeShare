package com.android.swingwizards.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.swingwizards.data.repository.UserRepo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileScreenViewModel(private val userRepo: UserRepo) : ViewModel() {

    private val _userUserName = MutableLiveData<String>()
    val username = _userUserName


    fun getUsername() {
        viewModelScope.launch {
            userRepo.getUsername.collectLatest { isSignedUp ->
                _userUserName.value = isSignedUp
            }
        }
    }
}