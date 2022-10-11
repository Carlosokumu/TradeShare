package com.example.smarttrader.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.core.network.data.models.ChatDetails
import com.example.core.network.repository.ChatRepository
import com.example.smarttrader.models.WebSocketState
import com.tinder.scarlet.WebSocket
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ForumViewModel(private val chatRepository: ChatRepository): ViewModel() {


    private val _webState: MutableStateFlow<WebSocketState> = MutableStateFlow(
        WebSocketState.ConnectionInitializing
    )
    val  webState = _webState.asStateFlow()




    @SuppressLint("CheckResult")
    fun observeConnection(){
          chatRepository.observeConnection().observeOn(AndroidSchedulers.mainThread()).subscribe { response ->
               handleEvent(response)
          }
    }


    private fun handleEvent(response: WebSocket.Event){
        when (response) {
            is WebSocket.Event.OnConnectionOpened<*> ->{
                _webState.value = WebSocketState.ConnectionOpened
            }
            is WebSocket.Event.OnConnectionClosed -> {
                _webState.value = WebSocketState.ConnectionClosed
            }
            is WebSocket.Event.OnConnectionClosing ->  {
                _webState.value = WebSocketState.ConnectionClosing
            }
            is WebSocket.Event.OnConnectionFailed -> {
                _webState.value = WebSocketState.ConnectionFailed
            }
            is WebSocket.Event.OnMessageReceived -> {
                _webState.value = WebSocketState.MessageReceived(message = response.message)
            }

        }
    }

    fun sendMessage(chatDetails: ChatDetails) = chatRepository.sendMessage(chatDetails)

}