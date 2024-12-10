package com.bhyb.celestenote.ui.screen.note.noteoperation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhyb.celestenote.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteOperationViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

    private val _isUpload = MutableStateFlow(false)
    val isUpload : StateFlow<Boolean> = _isUpload

    fun onGetNote(noteId: Int) {
        viewModelScope.launch {
            val note =noteUseCases.getNote.invoke(noteId)
            note?.let {
                _isUpload.value = it.isUpload
            }
        }
    }

    fun onUpload(noteId: Int) {
        viewModelScope.launch {
            val note = noteUseCases.getNote(noteId)
            if (note != null) {
                note.isUpload = true
                noteUseCases.updateNote(note)
                _isUpload.value = note.isUpload
            }
        }
    }

    fun onDeleteNoteToRecycle(noteId: Int) {
        viewModelScope.launch {
            val note = noteUseCases.getNote(noteId)
            if (note != null) {
                note.isDelete = true
            }
            note?.let { noteUseCases.updateNote(it) }
        }
    }
}