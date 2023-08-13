package com.example.core.network.data.api

import android.annotation.SuppressLint
import android.util.Log
import com.example.core.network.repository.CTraderRepository
import com.tinder.scarlet.WebSocket
import io.reactivex.android.schedulers.AndroidSchedulers

@SuppressLint("CheckResult")
class WebsocketManager(
    cTraderRepository: CTraderRepository,
    private val websocketConnectionListener: WebsocketConnectionListener
) {



    init {
        cTraderRepository.observeConnection().observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                handleEvent(response)
            }
    }


    private fun handleEvent(response: WebSocket.Event) {
        when (response) {
            is WebSocket.Event.OnConnectionOpened<*> -> {
                websocketConnectionListener.onConnected()
            }

            is WebSocket.Event.OnConnectionClosed -> {
                websocketConnectionListener.onDisconnected()
            }
            is WebSocket.Event.OnConnectionClosing -> {
                websocketConnectionListener.onDisconnected()
            }
            is WebSocket.Event.OnConnectionFailed -> {
                websocketConnectionListener.onDisconnected()
            }
            is WebSocket.Event.OnMessageReceived -> {
                val message = response.message
                websocketConnectionListener.onMessageReceived(message)

            }
            else -> {}
        }
    }
}