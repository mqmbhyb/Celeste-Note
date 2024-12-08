package com.bhyb.celestenote.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int = 0,

    val title: String,

    @SerialName("is_deletable")
    @Contextual
    val isDeletable: Boolean = true
)