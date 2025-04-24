package com.bhyb.celestenote.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bhyb.celestenote.domain.model.remote.Note
import com.bhyb.celestenote.domain.usecase.remote.noteusecase.GetNotes

class NotePagingSource(
    private val getNotes: GetNotes,
    private val pageSize: Int
): PagingSource<Int, Note>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Note> {
        return try {
            val page = params.key ?: 1
            val notes = getNotes.invoke(page, pageSize).data.records

            LoadResult.Page(
                data = notes,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (notes.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Note>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}