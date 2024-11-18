package com.bhyb.celestenote.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class LocalUserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val password: String,
    val email: String? = null,
    val telephone: Int? = null,
    val icon: String? = null
)
