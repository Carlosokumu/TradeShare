package com.example.core.network.data.api

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface EconomicCalendarService {

    @Receive
    fun observeConnection(): Flowable<WebSocket.Event>


    @Send
    fun sendMessage(message: ByteArray)
}