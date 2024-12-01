package com.bhyb.celestenote.data.repository

import com.bhyb.celestenote.data.dao.NoteDao
import com.bhyb.celestenote.data.util.toConvert
import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val noteDao: NoteDao
): NoteRepository {
    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note.toConvert())
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toConvert())
    }

    override fun getNotes(isDelete: Boolean): Flow<List<Note>> {
        return noteDao.getNotes(isDelete).map { it.map { result -> result.toConvert() } }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)?.toConvert()
    }

    override fun getNoteByCategory(categoryId: Int): Flow<List<Note>> {
        return noteDao.getNoteByCategory(categoryId).map { it.map { result -> result.toConvert() } }
    }

    override suspend fun deleteNotesByIds(noteIds: List<Int>) {
        noteDao.deleteNotesByIds(noteIds)
    }

    override fun queryNotesLike(searchQuery: String): Flow<List<Note>> {
        return noteDao.queryNotesLike(searchQuery).map { it.map { result -> result.toConvert() } }
    }

}