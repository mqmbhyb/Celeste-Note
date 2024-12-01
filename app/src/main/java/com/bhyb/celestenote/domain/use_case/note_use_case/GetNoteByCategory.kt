package com.bhyb.celestenote.domain.use_case.note_use_case

import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNoteByCategory(
    private  val repository: NoteRepository
) {
    operator fun invoke(
        categoryId: Int
    ): Flow<List<Note>> {
        return repository.getNoteByCategory(categoryId)
    }
}