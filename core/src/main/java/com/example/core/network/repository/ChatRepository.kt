package com.example.core.network.repository

import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable

interface ChatRepository {


    fun observeConnection(): Flowable<WebSocket.Event>
}