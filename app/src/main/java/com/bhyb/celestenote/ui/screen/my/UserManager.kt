package com.bhyb.celestenote.ui.screen.my

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.bhyb.celestenote.domain.model.remote.User

val LocalUserManager = staticCompositionLocalOf<UserManager> {
    error("UserManager not provided!")
}

class UserManager {
    var currentUser by mutableStateOf<User?>(null)
        private set

    fun login(user: User) {
        currentUser = user
    }

    fun logout() {
        currentUser = null
    }
}