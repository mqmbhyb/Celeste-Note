package com.bhyb.celestenote.ui.screen.note.noteoperation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhyb.celestenote.domain.model.Category
import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.domain.usecase.CategoryUseCases
import com.bhyb.celestenote.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteOperationViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val categoryUseCases: CategoryUseCases,
): ViewModel() {

    private val _isUpload = MutableStateFlow(false)
    val isUpload : StateFlow<Boolean> = _isUpload

    private val _isLock = MutableStateFlow(false)
    val isLock : StateFlow<Boolean> = _isLock
    
    private val _currentCategoryId = MutableStateFlow(0)
    val currentCategoryId : StateFlow<Int> = _currentCategoryId

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _newCategoryId = MutableStateFlow(0)
    private val newCategoryId : StateFlow<Int> = _newCategoryId

    fun setNewCategoryId(value: Int) {
        _newCategoryId.value = value
    }

    fun onGetNote(noteId: Int) {
        viewModelScope.launch {
            val note =noteUseCases.getNote.invoke(noteId)
            note?.let {
                _isUpload.value = it.isUpload
                _isLock.value = it.isLock
                _currentCategoryId.value = it.categoryId
            }
        }
    }

    private suspend fun updateNoteProperty(noteId: Int, updateBlock: (Note) -> Unit) {
        val note = noteUseCases.getNote.invoke(noteId)
        note?.apply {
            updateBlock(this)
            noteUseCases.updateNote(this)
        }
    }

    fun onMoveNote(noteId: Int) {
        viewModelScope.launch {
            updateNoteProperty(noteId) { it.categoryId = newCategoryId.value }
        }
    }

    fun onLockNote(noteId: Int) {
        viewModelScope.launch {
            updateNoteProperty(noteId) { it.isLock = !it.isLock; _isLock.value = it.isLock }
        }
    }

    fun onUploadNote(noteId: Int) {
        viewModelScope.launch {
            updateNoteProperty(noteId) { it.isUpload = true; _isUpload.value = true }
        }
    }

    fun onDeleteNoteToRecycle(noteId: Int) {
        viewModelScope.launch {
            updateNoteProperty(noteId) { it.isDelete = true }
        }
    }
    
    fun getCategories() {
        viewModelScope.launch {
            categoryUseCases.getCustomizedCategories.invoke().collect { categories ->
                _categories.value = categories
            }
        }
    }
}