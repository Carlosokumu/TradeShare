package com.example.smarttrader.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.network.data.api.ApiCallResult
import com.example.core.network.data.models.MtPosition
import com.example.core.network.data.models.MtPositions
import com.example.core.network.data.models.Position
import com.example.core.network.repository.OpenPositionsRepo
import com.example.smarttrader.models.MtFetchState
import com.example.smarttrader.models.OpenPositionFetchState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PositionsViewModel(
    private val positionsRepo: OpenPositionsRepo,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState: MutableStateFlow<OpenPositionFetchState> =
        MutableStateFlow(OpenPositionFetchState.Loading)
    val uiState = _uiState.asStateFlow()
    private val _mtFetchState: MutableStateFlow<MtFetchState> = MutableStateFlow(
       MtFetchState.Loading
    )
    val mtFetchState = _mtFetchState.asStateFlow()

    private val _positions: MutableLiveData<List<MtPosition>> = MutableLiveData()
    val positions : LiveData<List<MtPosition>> get() = _positions

    private val mpositions: MutableLiveData<List<Position>> = MutableLiveData()
    val profileModel: LiveData<List<Position>> get() = mpositions





    fun getOpenPositions() {
        viewModelScope.launch(coroutineDispatcher) {
            when (val result = positionsRepo.getOpenPositions()) {
                is ApiCallResult.ApiCallError -> {
                    _uiState.value = OpenPositionFetchState.Error("Could not update table")
                }
                is ApiCallResult.ServerError -> {
                    _uiState.value = OpenPositionFetchState.Error("Could not update table")
                }
                is ApiCallResult.Success -> {
                    _uiState.value = OpenPositionFetchState.Success(result.data)
                    mpositions.postValue(result.data.openpositions)
                }
            }
        }
    }


    fun getAllMtPositions() {
        viewModelScope.launch(coroutineDispatcher) {
            when (val mtPositions = positionsRepo.getMtPositions()) {
                is ApiCallResult.ApiCallError -> {
                    _mtFetchState.value = MtFetchState.Error("Could not fetch data")
                }
                is ApiCallResult.ServerError -> {
                    _mtFetchState.value = MtFetchState.Error(message = mtPositions.errorBody?.message.toString())
                }
                is ApiCallResult.Success -> {
                    _mtFetchState.value = MtFetchState.Success(positions = mtPositions.data.positions)
                    _positions.postValue( mtPositions.data.positions)
                }
            }
        }
    }


}