package com.bhyb.celestenote.domain.repository

import com.bhyb.celestenote.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insertNote(note: Note)

    suspend fun updateNote(note: Note)

    fun getNotes(isDelete: Boolean): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun getNoteByCategory(category: Int): Note?

    suspend fun deleteNotesByIds(noteIds: List<Int>)

    fun queryNotesLike(searchQuery: String): Flow<List<Note>>
}