package com.bhyb.celestenote.domain.usecase.remote.noteusecase

import com.bhyb.celestenote.domain.model.remote.ApiResult
import com.bhyb.celestenote.domain.model.remote.NotePaging
import com.bhyb.celestenote.domain.repository.remote.NoteRepository

class GetNotes(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(page: Int, pageSize: Int): ApiResult<NotePaging> {
        return repository.getNotes(page, pageSize)
    }
}