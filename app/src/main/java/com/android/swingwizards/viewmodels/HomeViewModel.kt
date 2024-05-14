package com.android.swingwizards.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.swingwizards.data.repository.UserRepo
import com.example.core.network.repository.EconomicCalendarRepo
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.tinder.scarlet.Message
import com.tinder.scarlet.WebSocket
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val economicRepo: EconomicCalendarRepo,
    private val userRepo: UserRepo
) : ViewModel() {


    private val _eventData = MutableLiveData<EventData>()
    val eventData = _eventData

    private val _userUserName = MutableLiveData<String>()
    val username = _userUserName

    init {
        observeConnection()
    }


    init {
        getUsername()
    }


    private fun getUsername() {
        viewModelScope.launch {
            userRepo.getUsername.collectLatest { username ->
                _userUserName.value = username
            }
        }
    }

    @SuppressLint("CheckResult")
    fun observeConnection() {
        economicRepo.observeConnection().observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                handleEvent(response)
            }
    }


    private fun handleEvent(response: WebSocket.Event) {
        when (response) {
            is WebSocket.Event.OnConnectionOpened<*> -> {
                Log.d("WEBSOCKET:", "connection open")
            }

            is WebSocket.Event.OnConnectionClosed -> {
                Log.d("WEBSOCKET:", "connection closed")
            }

            is WebSocket.Event.OnConnectionClosing -> {
                Log.d("WEBSOCKET:", "connection closing")
            }

            is WebSocket.Event.OnConnectionFailed -> {

                Log.d("WEBSOCKET:", "connection failed with: ${response.throwable}")
            }

            is WebSocket.Event.OnMessageReceived -> {
                val data = Gson().fromJson(response.message.toValue(), EventData::class.java)
                _eventData.value = data
                Log.d("WEBSOCKET:", "on message received with data: $data")
            }

        }
    }


}


private fun Message.toValue(): String {
    return when (this) {
        is Message.Text -> value
        is Message.Bytes -> value.toString()

    }
}

data class EventData(
    @SerializedName("data")
    val data: List<EventItem>
)


data class EventItem(
    @SerializedName("time")
    val time: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("actual")
    val actual: String,
    @SerializedName("forecast")
    val forecast: String,
    @SerializedName("previous")
    val previous: String,
)