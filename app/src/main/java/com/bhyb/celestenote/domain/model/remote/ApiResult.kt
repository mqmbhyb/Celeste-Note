package com.bhyb.celestenote.domain.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class ApiResult<T>(
    val code: Int,
    val msg: String,
    val data: T? = null
)