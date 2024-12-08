package com.bhyb.celestenote.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    @ColumnInfo(name = "is_deletable")
    val isDeletable: Boolean = true // 默认可删除
)
