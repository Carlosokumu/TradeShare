package com.android.swingwizards.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.swingwizards.data.repository.UserRepo
import com.carlos.core_database.entities.TradingPlatformEntity
import com.carlos.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SignUpProcessViewModel(
    private val userRepository: UserRepository,
    private val userRepo: UserRepo
) :
    ViewModel() {

    suspend fun setPlatformAsSelected(id: Int) =
        userRepository.setPlatformAsSelected(id = id)

    private val _tradingPlatforms =
        MutableStateFlow<List<TradingPlatformEntity>>(emptyList())
    val tradingPlatforms get() = _tradingPlatforms.asStateFlow()


    private val _isUserSignUp = MutableLiveData<Boolean>()
    val isUserSignUp = _isUserSignUp


    private val _isSignUp = MutableStateFlow(true)
    val isSignUp: StateFlow<Boolean> = _isSignUp


    init {
        getTradingPlatforms()
    }


    fun isUserSignUp() {
        viewModelScope.launch {
            userRepo.isUserLoggedIn.collectLatest { isSignedUp ->
                _isUserSignUp.value = isSignedUp
            }
        }
    }


    val selectedPlatform =
        userRepository.getSelectedTradingPlatform()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                null
            )

    private fun getTradingPlatforms() {
        viewModelScope.launch {
            userRepository.getTradingPlatforms().collectLatest {
                _tradingPlatforms.emit(it)
            }
        }
    }

    fun selectTradingPlatform(tradingPlatformEntity: TradingPlatformEntity) {
        Log.d("TradingPlatformEntity",tradingPlatformEntity.toString())
        viewModelScope.launch {
            if (tradingPlatformEntity.isSelected) {
                userRepository.setPlatformAsUnSelected(tradingPlatformEntity.id)
            } else {
                userRepository.unselectAllTradingPlatforms()
                userRepository.setPlatformAsSelected(tradingPlatformEntity.id)
            }
        }
    }

}