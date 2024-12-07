package com.bhyb.celestenote.domain.usecase.noteusecase

import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNoteByIsLock(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getNoteByIsLock()
    }
}