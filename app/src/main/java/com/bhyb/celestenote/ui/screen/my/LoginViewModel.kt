package com.bhyb.celestenote.ui.screen.my

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhyb.celestenote.domain.model.remote.ApiResult
import com.bhyb.celestenote.domain.model.remote.User
import com.bhyb.celestenote.domain.usecase.remote.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCase: UserUseCase
): ViewModel() {
    private val _loginState = MutableStateFlow<ApiResult<User>?>(null)
    val loginState: StateFlow<ApiResult<User>?> = _loginState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error.asStateFlow()

    fun login(name: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = ""

            try {
                val result = userUseCase.userLogin(name, password.toInt())
                _loginState.value = result

                Log.d(TAG, "登录结果: ${loginState.value?.data}")

                if (result.code != 1) {
                    _error.value = result.msg
                }
            } catch (e: Exception) {
                _error.value = "网络错误，请稍后重试"
                Log.e(TAG, "错误", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}