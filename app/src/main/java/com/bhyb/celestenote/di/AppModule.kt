package com.bhyb.celestenote.di

import android.app.Application
import androidx.room.Room
import com.bhyb.celestenote.data.AppDatabase
import com.bhyb.celestenote.data.repository.NoteRepositoryImpl
import com.bhyb.celestenote.domain.repository.NoteRepository
import com.bhyb.celestenote.domain.use_case.NoteUseCases
import com.bhyb.celestenote.domain.use_case.note_use_case.AddNote
import com.bhyb.celestenote.domain.use_case.note_use_case.DeleteNotes
import com.bhyb.celestenote.domain.use_case.note_use_case.GetNotes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: AppDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao())
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            addNote = AddNote(repository),
            getNotes = GetNotes(repository),
            deleteNotes = DeleteNotes(repository)
        )
    }
}