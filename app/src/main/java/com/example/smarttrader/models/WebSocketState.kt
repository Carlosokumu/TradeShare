package com.example.smarttrader.models

import com.tinder.scarlet.Message


/*
    Helps switch between different states the web socket connection can be at
 */
sealed class WebSocketState {

    object ConnectionInitializing: WebSocketState()
    object ConnectionClosed: WebSocketState()
    object ConnectionClosing: WebSocketState()
    object ConnectionOpened: WebSocketState()
    object ConnectionFailed: WebSocketState()
    data class MessageReceived(val message: Message): WebSocketState()
}