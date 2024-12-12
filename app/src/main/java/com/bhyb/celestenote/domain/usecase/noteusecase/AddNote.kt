package com.bhyb.celestenote.domain.usecase.noteusecase

import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note): Long {
        return repository.insertNote(note)
    }
}