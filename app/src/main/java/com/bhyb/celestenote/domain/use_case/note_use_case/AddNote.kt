package com.bhyb.celestenote.domain.use_case.note_use_case

import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.domain.repository.NoteRepository

class AddNote(
    private  val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.insertNote(note)
    }
}