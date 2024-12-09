package com.bhyb.celestenote.ui.screen.note.drawer

sealed class DeleteCategoryEvent() {
    object DeleteCategoryAndNotes: DeleteCategoryEvent()
    object DeleteCategory: DeleteCategoryEvent()
}