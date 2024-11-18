package com.bhyb.celestenote.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bhyb.celestenote.data.dao.CategoryDao
import com.bhyb.celestenote.data.dao.LocalUserDao
import com.bhyb.celestenote.data.dao.NoteDao
import com.bhyb.celestenote.data.entity.CategoryEntity
import com.bhyb.celestenote.data.entity.LocalUserEntity
import com.bhyb.celestenote.data.entity.NoteEntity
import com.bhyb.celestenote.data.util.NoteConverter

@Database(entities = [NoteEntity::class, CategoryEntity::class, LocalUserEntity::class], version = 1, exportSchema = false)
@TypeConverters(NoteConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun categoryDao(): CategoryDao

    abstract fun localUserDao(): LocalUserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "celeste_note_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}