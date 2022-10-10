package com.example.core.network.repository

import com.example.core.di.webSocketService
import com.example.core.network.data.api.WebSocketService
import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable

class ChatRepositoryImp(private val webSocketService: WebSocketService) : ChatRepository {



    override fun observeConnection(): Flowable<WebSocket.Event> {
       return webSocketService.observeConnection()
    }


}