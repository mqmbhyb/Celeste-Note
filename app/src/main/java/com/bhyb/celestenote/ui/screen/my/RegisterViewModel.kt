package com.bhyb.celestenote.ui.screen.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhyb.celestenote.domain.usecase.remote.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userUseCase: UserUseCase
): ViewModel(){

    private var _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    fun register(name: String, password: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = userUseCase.userRegister(name, password.toInt())
            _uiState.value = if (result.code == 1) {
                UiState.Success
            } else {
                UiState.Error(result.msg)
            }
        }
    }

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data object Success : UiState()
        data class Error(val errorMessage: String) : UiState()
    }
}