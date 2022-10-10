package com.example.core.network.data.api

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import io.reactivex.Flowable

interface WebSocketService {


    @Receive
    fun observeConnection(): Flowable<WebSocket.Event>
}