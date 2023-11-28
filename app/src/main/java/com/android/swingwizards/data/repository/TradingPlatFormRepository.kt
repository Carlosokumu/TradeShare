package com.android.swingwizards.data.repository

import com.android.swingwizards.data.local.dao.TradingPlatformDao

class TradingPlatFormRepository(private val tradingPlatformDao: TradingPlatformDao) {


    suspend fun setPlatformAsSelected(id: Int) = tradingPlatformDao.markPlatFormAsSelected(id = id)
    suspend fun setPlatformAsUnSelected(id: Int) =
        tradingPlatformDao.markPlatFormAsUnSelected(id = id)




    suspend fun  unselectAllTradingPlatforms() = tradingPlatformDao.unSelectAllPlatforms()
    fun getSelectedTradingPlatform() = tradingPlatformDao.getSelectedPlatform()


    fun getTradingPlatforms() = tradingPlatformDao.getTradingPlatforms()



}