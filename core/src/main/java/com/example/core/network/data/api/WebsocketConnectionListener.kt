package com.example.core.network.data.api

import com.tinder.scarlet.Message

interface WebsocketConnectionListener {
    fun onConnected();
    fun onMessageReceived(message: Message)
    fun onDisconnected();
}