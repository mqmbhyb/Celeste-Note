package com.bhyb.celestenote.data.util

import androidx.room.TypeConverter
import java.util.Date

class NoteConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}