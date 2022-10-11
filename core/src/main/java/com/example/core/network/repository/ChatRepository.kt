package com.example.core.network.repository

import com.example.core.network.data.models.ChatDetails
import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable

interface ChatRepository {


    fun observeConnection(): Flowable<WebSocket.Event>


    fun sendMessage(chatDetails: ChatDetails)
}