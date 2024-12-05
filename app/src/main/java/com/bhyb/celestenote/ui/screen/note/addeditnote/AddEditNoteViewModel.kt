package com.bhyb.celestenote.ui.screen.note.addeditnote

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhyb.celestenote.domain.model.InvalidNoteException
import com.bhyb.celestenote.domain.model.Note
import com.bhyb.celestenote.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    
    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(hint = "标题")
    )
    val noteTitle : State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextFieldState(hint = "请输入正文")
    )
    val noteContent : State<NoteTextFieldState> = _noteContent

    private val _eventFlow = MutableSharedFlow<ClickEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _originalTitle = mutableStateOf(NoteTextFieldState(hint = "标题"))
    val originalTitle: State<NoteTextFieldState> = _originalTitle

    private val _originalContent = mutableStateOf(NoteTextFieldState(hint = "请输入正文"))
    val originalContent: State<NoteTextFieldState> = _originalContent

    val isModified: Boolean
        get() = _noteTitle.value.text != originalTitle.value.text ||
                _noteContent.value.text != originalContent.value.text

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = note.title?.let {
                            noteTitle.value.copy(
                                text = it,
                                isHintVisible = it.isBlank()
                            )
                        }!!
                        _noteContent.value = note.content?.let {
                            noteContent.value.copy(
                                text = it,
                                isHintVisible = it.isBlank()
                            )
                        }!!
                        _originalTitle.value = _noteTitle.value
                        _originalContent.value = _noteContent.value
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {

            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                createTime = Date(),
                                updateTime = Date(),
                                categoryId = 1,
                                isDelete = false,
                                isUpload = false,
                                id = currentNoteId
                            )
                        )
                        _originalTitle.value = _noteTitle.value.copy()
                        _originalContent.value = _noteContent.value.copy()
                        _eventFlow.emit(ClickEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            ClickEvent.ShowToast(
                                message = e.message ?: "笔记保存失败"
                            )
                        )
                    }
                }
            }

            is AddEditNoteEvent.DeleteNotes -> {
                viewModelScope.launch {
                    try {
                        currentNoteId?.let { id ->
                            noteUseCases.deleteNotes(listOf(id))
                        }
                        _originalTitle.value = NoteTextFieldState(hint = "标题")
                        _originalContent.value = NoteTextFieldState(hint = "请输入正文")
                        _eventFlow.emit(ClickEvent.ShowToast(message = "笔记已删除"))
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            ClickEvent.ShowToast(
                                message = e.message ?: "删除笔记失败"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class ClickEvent {
        data class ShowToast(val message: String): ClickEvent()
        object SaveNote: ClickEvent()
        // todo 其他点击事件
    }
}