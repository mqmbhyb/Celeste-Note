package com.bhyb.celestenote.domain.use_case.note_use_case


import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotes(
    private  val repository: NoteRepository
) {
    operator fun invoke(
        isDelete: Boolean = false
    ): Flow<List<Note>> {
        return repository.getNotes(isDelete)
    }
}