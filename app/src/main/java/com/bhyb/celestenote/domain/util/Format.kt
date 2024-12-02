package com.bhyb.celestenote.domain.util

import android.annotation.SuppressLint
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@SuppressLint("NewApi")
fun formatDateTime(date: Date): String {
    val instant = date.toInstant()
    val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
    val formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm")
    return formatter.format(localDateTime)
}