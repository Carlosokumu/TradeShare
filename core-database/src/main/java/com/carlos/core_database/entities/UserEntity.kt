package com.carlos.core_database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["username"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var email: String,
    var username: String,
)