package com.bhyb.celestenote.domain.use_case

import com.bhyb.celestenote.domain.use_case.note_use_case.AddNote
import com.bhyb.celestenote.domain.use_case.note_use_case.DeleteNotes
import com.bhyb.celestenote.domain.use_case.note_use_case.GetNoteByCategory
import com.bhyb.celestenote.domain.use_case.note_use_case.GetNotes

data class NoteUseCases(
    val getNotes: GetNotes,
    val addNote: AddNote,
    val deleteNotes: DeleteNotes,
    val getNoteByCategory: GetNoteByCategory
)
