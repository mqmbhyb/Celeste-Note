package com.bhyb.celestenote.ui.screen.note.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _items = MutableStateFlow<List<Note>>(emptyList())
    val items: StateFlow<List<Note>> = _items.asStateFlow()

    fun onSearch() {
        viewModelScope.launch {
            combine(_searchQuery, noteUseCases.queryNotesLike.invoke(_searchQuery.value)) { query, items ->
                Pair(query, items)
            }.collect { (query, items) ->
                if (query == _searchQuery.value) {
                    _items.value = items
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}