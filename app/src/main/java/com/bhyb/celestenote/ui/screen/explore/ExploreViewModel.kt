package com.bhyb.celestenote.ui.screen.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bhyb.celestenote.data.paging.NotePagingSource
import com.bhyb.celestenote.domain.model.remote.Note
import com.bhyb.celestenote.domain.usecase.remote.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

    companion object {
        private const val PAGE_SIZE = 6
    }

    val notes: Flow<PagingData<Note>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { NotePagingSource(noteUseCases.getNotes, PAGE_SIZE) }
    ).flow.cachedIn(viewModelScope)
}