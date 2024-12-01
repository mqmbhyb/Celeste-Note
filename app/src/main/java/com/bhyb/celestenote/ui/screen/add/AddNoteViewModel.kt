package com.bhyb.celestenote.ui.screen.add

import androidx.lifecycle.ViewModel
import com.bhyb.celestenote.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

}