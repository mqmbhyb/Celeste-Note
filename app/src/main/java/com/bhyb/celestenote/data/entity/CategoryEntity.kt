package com.bhyb.celestenote.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val icon: Int,
    val is_deletable: Boolean = true // 默认可删除
)
