package com.bhyb.celestenote.domain.usecase.remote.userusecase

import com.bhyb.celestenote.domain.model.remote.ApiResult
import com.bhyb.celestenote.domain.model.remote.User
import com.bhyb.celestenote.domain.repository.remote.UserRepository

class UserLogin(
    private val repository: UserRepository
) {
    suspend operator fun invoke(name: String, password: Int): ApiResult<User> {
        return repository.login(name, password)
    }
}