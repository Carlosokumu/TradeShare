package com.example.smarttrader.data.local.convertors

import androidx.room.TypeConverter
import com.example.smarttrader.data.local.entity.SymbolModelEntity
import com.example.smarttrader.models.SymbolModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SymbolModelListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): List<SymbolModel> {
        val listType = object : TypeToken<List<SymbolModel>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<SymbolModel>): String {
        return gson.toJson(list)
    }
}