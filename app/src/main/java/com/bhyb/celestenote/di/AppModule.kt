package com.bhyb.celestenote.di

import android.app.Application
import androidx.room.Room
import com.bhyb.celestenote.data.AppDatabase
import com.bhyb.celestenote.data.repository.CategoryRepositoryImpl
import com.bhyb.celestenote.data.repository.NoteRepositoryImpl
import com.bhyb.celestenote.domain.repository.CategoryRepository
import com.bhyb.celestenote.domain.repository.NoteRepository
import com.bhyb.celestenote.domain.usecase.CategoryUseCases
import com.bhyb.celestenote.domain.usecase.NoteUseCases
import com.bhyb.celestenote.domain.usecase.categoryusecase.AddCategory
import com.bhyb.celestenote.domain.usecase.categoryusecase.DeleteCategory
import com.bhyb.celestenote.domain.usecase.categoryusecase.GetCategory
import com.bhyb.celestenote.domain.usecase.categoryusecase.GetCustomizedCategories
import com.bhyb.celestenote.domain.usecase.categoryusecase.UpdateCategory
import com.bhyb.celestenote.domain.usecase.noteusecase.AddNote
import com.bhyb.celestenote.domain.usecase.noteusecase.DeleteNotes
import com.bhyb.celestenote.domain.usecase.noteusecase.GetNote
import com.bhyb.celestenote.domain.usecase.noteusecase.GetNoteByCategory
import com.bhyb.celestenote.domain.usecase.noteusecase.GetNoteByIsLock
import com.bhyb.celestenote.domain.usecase.noteusecase.GetNotes
import com.bhyb.celestenote.domain.usecase.noteusecase.QueryNotesLike
import com.bhyb.celestenote.domain.usecase.noteusecase.UpdateNote
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
    fun provideCategoryRepository(db: AppDatabase): CategoryRepository {
        return CategoryRepositoryImpl(db.categoryDao())
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            addNote = AddNote(repository),
            getNotes = GetNotes(repository),
            deleteNotes = DeleteNotes(repository),
            getNoteByCategory = GetNoteByCategory(repository),
            getNote = GetNote(repository),
            updateNote = UpdateNote(repository),
            getNoteByIsLock = GetNoteByIsLock(repository),
            queryNotesLike = QueryNotesLike(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCategoryUseCases(repository: CategoryRepository): CategoryUseCases {
        return CategoryUseCases(
            addCategory = AddCategory(repository),
            deleteCategory = DeleteCategory(repository),
            getCategory = GetCategory(repository),
            getCustomizedCategories = GetCustomizedCategories(repository),
            updateCategory = UpdateCategory(repository)
        )
    }
}