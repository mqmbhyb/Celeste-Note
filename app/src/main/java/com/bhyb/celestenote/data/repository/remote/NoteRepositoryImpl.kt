package com.bhyb.celestenote.data.repository.remote

import com.bhyb.celestenote.data.network.NoteApi
import com.bhyb.celestenote.domain.model.remote.ApiResult
import com.bhyb.celestenote.domain.model.remote.NotePaging
import com.bhyb.celestenote.domain.repository.remote.NoteRepository

class NoteRepositoryImpl(private val apiService: NoteApi): NoteRepository{
    override suspend fun getNotes(page: Int, pageSize: Int): ApiResult<NotePaging> {
        return apiService.getNotes(page, pageSize)
    }
}