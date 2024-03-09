package com.example.core.network.repository

import com.example.core.network.data.api.EconomicCalendarService
import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable

class EconomicCalendarImpl(private val  economicCalendarService: EconomicCalendarService): EconomicCalendarRepo {
    override fun observeConnection(): Flowable<WebSocket.Event> {
        return economicCalendarService.observeConnection()
    }

    override fun sendMessage(message: ByteArray) {
        economicCalendarService.sendMessage(message)
    }
}