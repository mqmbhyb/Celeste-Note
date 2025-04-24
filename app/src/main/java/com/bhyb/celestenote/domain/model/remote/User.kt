package com.bhyb.celestenote.domain.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int? = null,
    val name: String,
    val password: Int,
    val email: String? = null,
    val telephone: Int? = null,
    val icon: String? = null
)