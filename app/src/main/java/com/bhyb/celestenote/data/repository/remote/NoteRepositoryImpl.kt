package com.bhyb.celestenote.data.repository.remote

import com.bhyb.celestenote.data.network.ApiService
import com.bhyb.celestenote.domain.model.remote.ApiResponse
import com.bhyb.celestenote.domain.repository.remote.NoteRepository

class NoteRepositoryImpl(private val apiService: ApiService): NoteRepository{
    override suspend fun getNotes(page: Int, pageSize: Int): ApiResponse {
        return apiService.getNotes(page, pageSize)
    }
}