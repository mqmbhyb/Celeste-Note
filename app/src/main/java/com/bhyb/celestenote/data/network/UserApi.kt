package com.bhyb.celestenote.data.network

import com.bhyb.celestenote.domain.model.remote.ApiResult
import com.bhyb.celestenote.domain.model.remote.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @POST("login")
    suspend fun login(@Body user: User): ApiResult<User>

    @POST
    suspend fun register(@Body user: User): ApiResult<Unit>

    @GET("{id}")
    suspend fun getUserById(@Path("id") id: Int): ApiResult<User>

    @PUT
    suspend fun updateUser(@Body user: User): ApiResult<Unit>
}