package com.bhyb.celestenote.domain.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val createTime: String,
    val updateTime: String,
    val authorId: Int,
    val isShow: Boolean,
    val star: Int,
    val collect: Int,
    val authorName: String,
    val authorIcon: String?
)

@Serializable
data class NotePaging(
    val total: Int,
    val records: List<Note>
)