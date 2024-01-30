package com.android.swingwizards.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.swingwizards.data.local.entity.TradingPlatformEntity
import com.android.swingwizards.data.repository.TradingPlatFormRepository
import com.android.swingwizards.data.repository.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SignUpProcessViewModel(
    private val tradingPlatFormRepository: TradingPlatFormRepository,
    private val userRepo: UserRepo
) :
    ViewModel() {

    suspend fun setPlatformAsSelected(id: Int) =
        tradingPlatFormRepository.setPlatformAsSelected(id = id)

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
        tradingPlatFormRepository.getSelectedTradingPlatform()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                null
            )

    private fun getTradingPlatforms() {
        viewModelScope.launch {
            tradingPlatFormRepository.getTradingPlatforms().collectLatest {
                _tradingPlatforms.emit(it)
            }
        }
    }

    fun selectTradingPlatform(tradingPlatformEntity: TradingPlatformEntity) {
        viewModelScope.launch {
            if (tradingPlatformEntity.isSelected) {
                tradingPlatFormRepository.setPlatformAsUnSelected(tradingPlatformEntity.id)
            } else {
                tradingPlatFormRepository.unselectAllTradingPlatforms()
                tradingPlatFormRepository.setPlatformAsSelected(tradingPlatformEntity.id)
            }
        }
    }

}