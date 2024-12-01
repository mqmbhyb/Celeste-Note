package com.bhyb.celestenote.data.util

import com.bhyb.celestenote.data.entity.NoteEntity
import com.bhyb.celestenote.domain.model.Note

fun NoteEntity.toConvert(): Note {
    return Note(
        id, title, content, createTime, updateTime, category, isDelete, isUpload
    )
}

fun Note.toConvert(): NoteEntity {
    return NoteEntity(
        id, title, content, createTime, updateTime, category, isDelete, isUpload
    )
}