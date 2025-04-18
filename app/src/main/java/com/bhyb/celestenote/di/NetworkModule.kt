package com.bhyb.celestenote.di

import com.bhyb.celestenote.data.network.ApiService
import com.bhyb.celestenote.data.repository.remote.NoteRepositoryImpl
import com.bhyb.celestenote.domain.repository.remote.NoteRepository
import com.bhyb.celestenote.domain.usecase.remote.NoteUseCases
import com.bhyb.celestenote.domain.usecase.remote.noteusecase.GetNotes
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

// 模拟器专用
private const val BASE_URL = "http://10.0.2.2:8080/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(apiService: ApiService): NoteRepository {
        return NoteRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideGetNotesUseCase(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
           getNotes = GetNotes(repository)
        )
    }
}