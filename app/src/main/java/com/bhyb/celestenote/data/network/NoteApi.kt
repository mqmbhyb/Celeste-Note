package com.bhyb.celestenote.data.network

import com.bhyb.celestenote.domain.model.remote.ApiResult
import com.bhyb.celestenote.domain.model.remote.NotePaging
import retrofit2.http.GET
import retrofit2.http.Query

interface NoteApi {
    @GET("notes")
    suspend fun getNotes(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResult<NotePaging>
}