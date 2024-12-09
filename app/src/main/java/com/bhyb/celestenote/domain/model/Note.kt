package com.bhyb.celestenote.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Note(
    val id: Int? = null,

    val title: String? = null,

    val content: String? = null,

    @SerialName("create_time")
    @Contextual
    val createTime: Date,

    @SerialName("update_time")
    @Contextual
    val updateTime: Date,

    @SerialName("category_id")
    var categoryId: Int,

    @SerialName("is_delete")
    var isDelete: Boolean,

    @SerialName("is_upload")
    val isUpload: Boolean,

    @SerialName("is_lock")
    val isLock: Boolean
)

class InvalidNoteException(message: String): Exception(message)
