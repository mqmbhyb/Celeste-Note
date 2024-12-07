package com.bhyb.celestenote.domain.usecase.noteusecase

import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNoteByCategory(
    private val repository: NoteRepository
) {
    operator fun invoke(
        categoryId: Int
    ): Flow<List<Note>> {
        return repository.getNoteByCategory(categoryId)
    }
}