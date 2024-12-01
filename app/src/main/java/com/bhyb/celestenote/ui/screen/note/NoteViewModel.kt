package com.bhyb.celestenote.ui.screen.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.domain.use_case.NoteUseCases
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

    init {
        onGetNotes()
        initNotes()
    }

    /* 获取全部笔记 */
    private fun onGetNotes() {
        viewModelScope.launch {
            noteUseCases.getNotes.invoke().collect { result ->
                _notes.value = result
            }
        }
    }

    fun onGetNoteByCategory(categoryId: Int) {
        viewModelScope.launch {
            noteUseCases.getNoteByCategory.invoke(categoryId).collect { result ->
                _notes.value = result
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
            content = "这是你的第一个笔记！点击加号新建更多笔记。",
            createTime = Date(),
            updateTime = Date(),
            categoryId = 0,
            isDelete = false,
            isUpload = false
        )
    }
}