package com.bhyb.celestenote.ui.screen.note.addeditnote

import java.util.Date

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)

data class StateDefForDate(
    val date: Date = Date()
)

data class StateDefForInt(
    var int: Int = 1
)

data class StateDefForBoolean(
    var boolean: Boolean = false
) {
    operator fun not() {
        boolean = !boolean
    }
}