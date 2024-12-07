package com.bhyb.celestenote.domain.usecase.noteusecase

import com.bhyb.celestenote.domain.repository.NoteRepository

class DeleteNotes(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteIds: List<Int>) {
        repository.deleteNotesByIds(noteIds)
    }
}