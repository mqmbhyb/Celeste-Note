package com.bhyb.celestenote.domain.repository.remote

import com.bhyb.celestenote.domain.model.remote.ApiResponse

interface NoteRepository {
    suspend fun getNotes(page: Int, pageSize: Int): ApiResponse
}