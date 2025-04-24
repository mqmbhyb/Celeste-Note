package com.bhyb.celestenote.data.repository.remote

import com.bhyb.celestenote.data.network.UserApi
import com.bhyb.celestenote.domain.model.remote.ApiResult
import com.bhyb.celestenote.domain.model.remote.User
import com.bhyb.celestenote.domain.repository.remote.UserRepository

class UserRepositoryImpl(private val apiService: UserApi): UserRepository {
    override suspend fun login(name: String, password: Int): ApiResult<User> {
        return apiService.login(User(name = name, password = password))
    }

    override suspend fun register(name: String, password: Int): ApiResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: Int): ApiResult<User> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User): ApiResult<Unit> {
        TODO("Not yet implemented")
    }
}