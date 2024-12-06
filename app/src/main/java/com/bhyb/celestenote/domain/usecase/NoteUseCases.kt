package com.bhyb.celestenote.domain.usecase

import com.bhyb.celestenote.domain.usecase.noteusecase.AddNote
import com.bhyb.celestenote.domain.usecase.noteusecase.DeleteNotes
import com.bhyb.celestenote.domain.usecase.noteusecase.GetNote
import com.bhyb.celestenote.domain.usecase.noteusecase.GetNoteByCategory
import com.bhyb.celestenote.domain.usecase.noteusecase.GetNotes
import com.bhyb.celestenote.domain.usecase.noteusecase.UpdateNote

data class NoteUseCases(
    val getNotes: GetNotes,
    val addNote: AddNote,
    val deleteNotes: DeleteNotes,
    val getNoteByCategory: GetNoteByCategory,
    val getNote: GetNote,
    val updateNote: UpdateNote
)
