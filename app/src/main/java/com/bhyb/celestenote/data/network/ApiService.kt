package com.bhyb.celestenote.data.network

import com.bhyb.celestenote.domain.model.remote.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("notes")
    suspend fun getNotes(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse
}