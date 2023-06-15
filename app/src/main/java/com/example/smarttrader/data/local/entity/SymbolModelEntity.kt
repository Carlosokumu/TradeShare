package com.example.smarttrader.data.local.entity

import androidx.room.*
import com.example.smarttrader.models.SymbolModel


@Entity(indices = [Index(value = ["Id"], unique = true)])
data class SymbolModelEntity(
    @PrimaryKey
    var Id: Long,
    var ConversionSymbols: List<SymbolModel>,
)











