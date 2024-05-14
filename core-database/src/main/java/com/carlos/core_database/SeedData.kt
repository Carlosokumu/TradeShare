package com.carlos.core_database

import com.carlos.core_database.entities.TradingPlatformEntity

object SeedData {

    val tradingPlatformEntityLists = listOf(
        TradingPlatformEntity(name = "Ctrader", logo = R.drawable.ctrader_logo_bird),
        TradingPlatformEntity(name = "Metatrader", logo = R.drawable.metatrader),
        TradingPlatformEntity(name = "Binance", logo = R.drawable.binance)
    )

}