package com.bhyb.celestenote.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey
    val id: Int? = null,

    val title: String? = null,

    val content: String? = null,

    @ColumnInfo(name = "create_time")
    val createTime: Date,

    @ColumnInfo(name = "update_time")
    val updateTime: Date,

    @ColumnInfo(name = "category_id")
    var categoryId: Int,

    @ColumnInfo(name = "is_delete")
    val isDelete: Boolean,

    @ColumnInfo(name = "is_upload")
    val isUpload: Boolean,

    @ColumnInfo(name = "is_lock")
    val isLock: Boolean
)