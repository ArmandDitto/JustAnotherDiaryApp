package com.example.justordinarydiaryapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.justordinarydiaryapp.data.repository.DiaryRepository
import com.example.justordinarydiaryapp.model.Diary
import kotlinx.coroutines.delay

class DiaryLocalPagingSource(
    private val repository: DiaryRepository.Local
) : PagingSource<Int, Diary>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Diary> {
        val page = params.key ?: 0

        return try {

            val diaries = repository.getDiariesPagingLocal(params.loadSize, page * params.loadSize)

            // Simulate page loading
            if (page != 0) delay(1000)

            LoadResult.Page(
                data = diaries,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (diaries.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Diary>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}