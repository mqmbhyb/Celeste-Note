package com.bhyb.celestenote.domain.usecase

import com.bhyb.celestenote.domain.usecase.noteusecase.AddNote
import com.bhyb.celestenote.domain.usecase.noteusecase.DeleteNotes
import com.bhyb.celestenote.domain.usecase.noteusecase.GetNoteByCategory
import com.bhyb.celestenote.domain.usecase.noteusecase.GetNotes

data class NoteUseCases(
    val getNotes: GetNotes,
    val addNote: AddNote,
    val deleteNotes: DeleteNotes,
    val getNoteByCategory: GetNoteByCategory
)
