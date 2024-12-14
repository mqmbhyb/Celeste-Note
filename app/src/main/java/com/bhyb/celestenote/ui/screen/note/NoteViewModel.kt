package com.bhyb.celestenote.ui.screen.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private val _notesByIsLock = MutableStateFlow<List<Note>>(emptyList())
    val notesByIsLock: StateFlow<List<Note>> = _notesByIsLock.asStateFlow()

    private val _isLockNote = MutableStateFlow(false)
    val isLockNote : StateFlow<Boolean> = _isLockNote

    init {
        onGetNotes()
        initNotes()
        onGetNoteByIsLock()
    }

    fun onGetNote(noteId: Int) {
        viewModelScope.launch {
            val note = noteUseCases.getNote.invoke(noteId)
            if (note != null) {
                _isLockNote.value = note.isLock
            }
        }
    }

    /* 获取全部笔记 */
    private fun onGetNotes() {
        viewModelScope.launch {
            noteUseCases.getNotes.invoke().collect { result ->
                _notes.value = result
            }
        }
    }

    private fun onGetNoteByIsLock() {
        viewModelScope.launch {
            noteUseCases.getNoteByIsLock.invoke().collect { result ->
                _notesByIsLock.value = result
            }
        }
    }

    /* 插入默认笔记 */
    private fun initNotes() {
        viewModelScope.launch {
            if (noteUseCases.getNotes.invoke().first().isEmpty()) {
                val welcomeNote = createWelcomeNote()
                noteUseCases.addNote(welcomeNote)
            }
        }
    }

    private fun createWelcomeNote(): Note {
        return Note(
            id = 0, // 如果是自增主键，设为 0 数据库会自动分配 ID
            title = "欢迎",
            content = "这是你的第一个笔记！点击加号新建更多笔记。短按查看笔记详情，长按进行编辑。",
            createTime = Date(),
            updateTime = Date(),
            categoryId = 0,
            isDelete = false,
            isUpload = false,
            isLock = false
        )
    }
}