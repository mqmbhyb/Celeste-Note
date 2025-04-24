package com.bhyb.celestenote.domain.repository.remote

import com.bhyb.celestenote.domain.model.remote.ApiResult
import com.bhyb.celestenote.domain.model.remote.NotePaging

interface NoteRepository {
    suspend fun getNotes(page: Int, pageSize: Int): ApiResult<NotePaging>
}