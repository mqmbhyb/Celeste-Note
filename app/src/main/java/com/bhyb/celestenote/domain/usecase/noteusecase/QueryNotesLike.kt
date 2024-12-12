package com.bhyb.celestenote.domain.usecase.noteusecase

import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class QueryNotesLike(
    private val repository: NoteRepository
) {
    operator fun invoke(
        searchQuery: String
    ): Flow<List<Note>> {
        return repository.queryNotesLike(searchQuery)
            .flowOn(Dispatchers.IO)
    }
}