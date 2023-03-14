package com.example.justordinarydiaryapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.justordinarydiaryapp.data.repository.DiaryRepository
import com.example.justordinarydiaryapp.model.Diary
import com.example.justordinarydiaryapp.network.parseRetrofitException
import retrofit2.HttpException
import java.io.IOException

class DiaryPagingSource(
    private val repository: DiaryRepository.Remote
) : PagingSource<Int, Diary>() {

    override fun getRefreshKey(state: PagingState<Int, Diary>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Diary> {
        val page = params.key ?: 1
        return try {
            val response = repository.getDiariesPaging(page)

            val data = response.data ?: listOf()
            val prevKey = if (page == 1) null else page - 1
            val nextKey: Int? = if (data.isEmpty()) null else page.plus(1)

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(Throwable(message = "Cannot connect to server: Please make sure you are connected to the Internet and try again."))
        } catch (e: HttpException) {
            return LoadResult.Error(Throwable(message = parseRetrofitException(e)))
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}