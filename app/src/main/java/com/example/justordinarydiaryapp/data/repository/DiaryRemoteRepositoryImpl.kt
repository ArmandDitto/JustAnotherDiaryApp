package com.example.justordinarydiaryapp.data.repository

import com.example.justordinarydiaryapp.data.dao.DiaryDao
import com.example.justordinarydiaryapp.data.entity.ArchivedDiaryEntity
import com.example.justordinarydiaryapp.data.entity.DiaryEntity
import com.example.justordinarydiaryapp.model.Diary
import com.example.justordinarydiaryapp.model.request.DiaryRequest
import com.example.justordinarydiaryapp.network.ApiService
import com.example.justordinarydiaryapp.network.model.PagingWrapper
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import com.example.justordinarydiaryapp.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DiaryRemoteRepositoryImpl(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val diaryDao: DiaryDao
) : DiaryRepository.Remote {

    override suspend fun createNewDiary(request: DiaryRequest): ResultWrapper<Diary> {
        return safeApiCall(dispatcher) { apiService.crateNewDiary(request) }
    }

    override suspend fun getDiaryDetail(diaryId: String): ResultWrapper<Diary> {
        return safeApiCall(dispatcher) { apiService.getDiaryDetail(diaryId) }
    }

    override suspend fun getEditDiary(
        diaryId: String,
        request: DiaryRequest
    ): ResultWrapper<Diary> {
        return safeApiCall(dispatcher) { apiService.editDiary(diaryId, request) }
    }

    override suspend fun getDiariesPaging(page: Int): PagingWrapper<List<Diary>> {
        return apiService.getDiariesPaging(page)
    }

    override suspend fun getDiaries(page: Int): ResultWrapper<PagingWrapper<List<Diary>>> {
        val result = safeApiCall(dispatcher) { apiService.getDiariesPaging(page) }
        if (result is ResultWrapper.Success) {
            result.value.data?.forEach {
                val diary = DiaryEntity.ModelMapper.fromDiary(it)
                diaryDao.insertData(diary)
            }
        }
        return result
    }

    override suspend fun archiveDiary(diaryId: String): ResultWrapper<Diary> {
        val result = safeApiCall(dispatcher) { apiService.archiveDiary(diaryId) }
        if (result is ResultWrapper.Success) {
            val archivedDiary = ArchivedDiaryEntity.ModelMapper.fromDiary(result.value)
            diaryDao.insertArchivedDiary(archivedDiary)
        }
        return result
    }

    override suspend fun unarchiveDiary(diaryId: String): ResultWrapper<Diary> {
        val result = safeApiCall(dispatcher) { apiService.unArchiveDiary(diaryId) }
        if (result is ResultWrapper.Success) {
            val archivedDiary = ArchivedDiaryEntity.ModelMapper.fromDiary(result.value)
            diaryDao.deleteArchivedDiary(archivedDiary)
        }
        return result
    }

}