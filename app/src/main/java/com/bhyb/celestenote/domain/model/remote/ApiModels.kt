package com.bhyb.celestenote.domain.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val code: Int,
    val msg: String,
    val data: DataResponse
)

@Serializable
data class DataResponse(
    val total: Int,
    val records: List<Record>
)

@Serializable
data class Record(
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