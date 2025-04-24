package com.bhyb.celestenote.domain.repository.remote

import com.bhyb.celestenote.domain.model.remote.ApiResult
import com.bhyb.celestenote.domain.model.remote.User

interface UserRepository {
    suspend fun login(name: String, password: Int): ApiResult<User>
    suspend fun register(name: String, password: Int): ApiResult<Unit>
    suspend fun getUserById(id: Int): ApiResult<User>
    suspend fun updateUser(user: User): ApiResult<Unit>
}