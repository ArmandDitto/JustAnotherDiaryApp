package com.example.justordinarydiaryapp.data.repository

import com.example.justordinarydiaryapp.model.Diary
import com.example.justordinarydiaryapp.model.request.DiaryRequest
import com.example.justordinarydiaryapp.network.ApiService
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import com.example.justordinarydiaryapp.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DiaryRepositoryImpl(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DiaryRepository {

    override suspend fun createNewDiary(request: DiaryRequest): ResultWrapper<Diary> {
        return safeApiCall(dispatcher) { apiService.crateNewDiary(request) }
    }

}