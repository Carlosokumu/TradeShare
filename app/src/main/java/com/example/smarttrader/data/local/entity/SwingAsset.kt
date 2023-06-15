package com.example.smarttrader.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(indices = [Index(value = ["assetId"], unique = true)])
data class SwingAsset(
    @PrimaryKey
    var assetId: Long,
    var name: String,
    var displayname: String,
    var digits: Int
)
