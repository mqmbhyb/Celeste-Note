package com.bhyb.celestenote.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bhyb.celestenote.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query("SELECT * FROM note WHERE is_delete = :isDelete ORDER BY create_time DESC")
    fun getNotes(isDelete: Boolean): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Query("SELECT * FROM note WHERE category_id = :categoryId and is_delete = 0")
    fun getNoteByCategory(categoryId: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE is_lock = 1")
    fun getNoteByIsLock(): Flow<List<NoteEntity>>

    //彻底删除
    @Query("DELETE FROM note WHERE id IN (:noteIds)")
    suspend fun deleteNotesByIds(noteIds: List<Int>)

    //搜索
    @Query("SELECT * FROM note WHERE LOWER(title) LIKE '%' || LOWER(:searchQuery) || '%' AND is_delete = 0 OR LOWER(content) LIKE '%' || LOWER(:searchQuery) || '%' AND is_delete = 0")
    fun queryNotesLike(searchQuery: String): Flow<List<NoteEntity>>
}