package com.bhyb.celestenote.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int = 0,

    @SerialName("category_title")
    @Contextual
    val categoryTitle: String,

    val icon: Int,

    @SerialName("is_deletable")
    @Contextual
    val isDeletable: Boolean = true
)