package com.bhyb.celestenote.data.util

import com.bhyb.celestenote.data.entity.CategoryEntity
import com.bhyb.celestenote.data.entity.NoteEntity
import com.bhyb.celestenote.domain.model.Category
import com.bhyb.celestenote.domain.model.Note

fun NoteEntity.toConvert(): Note {
    return Note(
        id, title, content, createTime, updateTime, categoryId, isDelete, isUpload, isLock
    )
}

fun Note.toConvert(): NoteEntity {
    return NoteEntity(
        id, title, content, createTime, updateTime, categoryId, isDelete, isUpload, isLock
    )
}

fun CategoryEntity.toConvert(): Category {
    return Category(
        id, title, isDeletable
    )
}

fun Category.toConvert(): CategoryEntity {
    return CategoryEntity(
        id, title, isDeletable
    )
}