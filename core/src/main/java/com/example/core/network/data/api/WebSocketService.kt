package com.example.core.network.data.api

import com.example.core.network.data.models.ChatDetails
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface WebSocketService {


    @Receive
    fun observeConnection(): Flowable<WebSocket.Event>


    @Send
    fun sendMessage(chatDetails: ChatDetails)
}