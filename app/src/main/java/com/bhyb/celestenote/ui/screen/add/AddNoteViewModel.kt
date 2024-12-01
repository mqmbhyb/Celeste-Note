package com.bhyb.celestenote.ui.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {
    fun onAddNote(note: Note) {
        viewModelScope.launch {
            noteUseCases.addNote.invoke(note)
        }
    }
}