package com.carlos.core_database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.carlos.core_database.entities.TradingPlatformEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TradingPlatformDao {

    @Query("UPDATE `TradingPlatformEntity` SET `isSelected` = 1 WHERE `id` = :id")
    suspend fun markPlatFormAsSelected(id: Int)

    @Query("UPDATE `TradingPlatformEntity` SET `isSelected` = 0 WHERE `id` = :id")
    suspend fun markPlatFormAsUnSelected(id: Int)


    @Query("UPDATE `TradingPlatformEntity` SET `isSelected` = 0")
    suspend fun unSelectAllPlatforms()


    @Query("SELECT * FROM `TradingPlatformEntity` ORDER BY `id` ASC")
    fun getTradingPlatforms(): Flow<List<TradingPlatformEntity>>


    @Query("SELECT * FROM `TradingPlatformEntity` WHERE `isSelected` = 1")
    fun getSelectedPlatform(): Flow<TradingPlatformEntity?>


    @Upsert
    suspend fun upsertTradingPlatforms(countries: List<TradingPlatformEntity>)

}