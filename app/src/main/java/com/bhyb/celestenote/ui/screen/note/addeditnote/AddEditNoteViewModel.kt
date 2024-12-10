package com.bhyb.celestenote.ui.screen.note.addeditnote

import androidx.compose.runtime.MutableState
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
) : ViewModel() {

    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(hint = "标题")
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextFieldState(hint = "请输入正文")
    )
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _eventFlow = MutableSharedFlow<ClickEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _originalTitle = mutableStateOf(NoteTextFieldState(hint = "标题"))
    private val originalTitle: State<NoteTextFieldState> = _originalTitle

    private val _originalContent = mutableStateOf(NoteTextFieldState(hint = "请输入正文"))
    private val originalContent: State<NoteTextFieldState> = _originalContent

    private var originalContentLength = 0
    private var originalTitleLength = 0

    val isModified: Boolean
        get() = _noteTitle.value.text != originalTitle.value.text ||
                _noteContent.value.text != originalContent.value.text

    val isCreateNote: Boolean
        get() = currentNoteId == null

    val isBlankNote : Boolean
        get() = noteTitle.value.text.isBlank() && noteContent.value.text.isBlank()

    val contentIncOrUnchangedOrTitleInc: Boolean
        get() {
            if (!isModified) return false
            val newContentLen = noteContent.value.text.length
            val newTitleLen = noteTitle.value.text.length
            // 笔记内容不为空且字数增加
            val contentIncOrUnchanged =
                noteContent.value.text.isNotBlank() && newContentLen > originalContentLength
            // 笔记内容为空时，标题字数增加
            val titleIncWhenEmpty =
                noteContent.value.text.isBlank() && newTitleLen > originalTitleLength

            return contentIncOrUnchanged || titleIncWhenEmpty
        }

    val contentDecOrTitleDec: Boolean
        get() {
            if (!isModified) return false
            val newContentLen = noteContent.value.text.length
            val newTitleLen = noteTitle.value.text.length
            // 笔记内容字数减少
            val contentDec = newContentLen < originalContentLength
            // 笔记内容为空时，标题字数减少
            val titleDecWhenEmpty =
                noteContent.value.text.isBlank() && newTitleLen < originalTitleLength

            return contentDec || titleDecWhenEmpty || isBlankNote
        }

    private var currentNoteId: Int? = null

    private val _noteCreateTime = mutableStateOf(StateDefForDate())
    private val noteCreateTime : MutableState<StateDefForDate> = _noteCreateTime

    private val _noteCategoryId = mutableStateOf(StateDefForInt())
    private val noteCategoryId: MutableState<StateDefForInt> = _noteCategoryId

    private val _noteIsDelete = mutableStateOf(StateDefForBoolean())
    val noteIsDelete: MutableState<StateDefForBoolean> = _noteIsDelete

    private val _noteIsUpload = mutableStateOf(StateDefForBoolean())
    val noteIsUpload: MutableState<StateDefForBoolean> = _noteIsUpload

    private val _noteIsLock = mutableStateOf(StateDefForBoolean())
    var noteIsLock: MutableState<StateDefForBoolean> = _noteIsLock

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
                        _noteCreateTime.value = note.createTime.let {
                            noteCreateTime.value.copy(date = it)
                        }
                        _noteCategoryId.value = note.categoryId.let {
                            noteCategoryId.value.copy(int = it)
                        }
                        _noteIsDelete.value = note.isDelete.let {
                            noteIsDelete.value.copy(boolean = it)
                        }
                        _noteIsUpload.value = note.isUpload.let {
                            noteIsUpload.value.copy(boolean = it)
                        }
                        _noteIsLock.value = note.isLock.let {
                            noteIsLock.value.copy(boolean = it)
                        }
                        _originalTitle.value = _noteTitle.value
                        _originalContent.value = _noteContent.value
                        originalContentLength = note.content.length
                        originalTitleLength = note.title.length
                    }
                }
            } else {
                originalContentLength = 0
                originalTitleLength = 0
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {

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
                                categoryId = 0,
                                isDelete = false,
                                isUpload = false,
                                isLock = false,
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
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            ClickEvent.ShowToast(
                                message = e.message ?: "删除笔记失败"
                            )
                        )
                    }
                }
            }

            AddEditNoteEvent.UpdateNote -> {
                viewModelScope.launch {
                    try {
                        val updatedNote = Note(
                            title = noteTitle.value.text,
                            content = noteContent.value.text,
                            createTime = noteCreateTime.value.date,
                            updateTime = Date(),
                            categoryId = noteCategoryId.value.int,
                            isDelete = noteIsDelete.value.boolean,
                            isUpload = noteIsUpload.value.boolean,
                            isLock = noteIsLock.value.boolean,
                            id = currentNoteId
                        )
                        noteUseCases.updateNote(updatedNote)

                        _originalTitle.value = _noteTitle.value.copy()
                        _originalContent.value = _noteContent.value.copy()
                        originalContentLength = noteContent.value.text.length
                        originalTitleLength = noteTitle.value.text.length
                        _eventFlow.emit(ClickEvent.UpdateNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            ClickEvent.ShowToast(
                                message = e.message ?: "更新笔记失败"
                            )
                        )
                    }
                }
            }

        }
    }

    sealed class ClickEvent {
        data class ShowToast(val message: String) : ClickEvent()
        object SaveNote : ClickEvent()
        object UpdateNote : ClickEvent()
    }
}