package com.bhyb.celestenote.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bhyb.celestenote.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Query("SELECT * FROM note WHERE category = :category")
    suspend fun getNoteByCategory(category: Int): NoteEntity?

    @Query("SELECT * FROM note ORDER BY create_time DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    //彻底删除
    @Query("DELETE FROM note WHERE id IN (:noteIds)")
    suspend fun deleteNotesByIds(noteIds: List<Int>)
}