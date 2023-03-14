package com.example.justordinarydiaryapp.data.repository

import com.example.justordinarydiaryapp.model.Diary
import com.example.justordinarydiaryapp.model.request.DiaryRequest
import com.example.justordinarydiaryapp.network.model.PagingWrapper
import com.example.justordinarydiaryapp.network.model.ResultWrapper

interface DiaryRepository {

    interface Remote {

        suspend fun createNewDiary(request: DiaryRequest): ResultWrapper<Diary>
        suspend fun getDiaryDetail(diaryId: String): ResultWrapper<Diary>
        suspend fun getEditDiary(diaryId: String, request: DiaryRequest): ResultWrapper<Diary>
        suspend fun getDiariesPaging(page: Int): PagingWrapper<List<Diary>>
        suspend fun getDiaries(page: Int): ResultWrapper<PagingWrapper<List<Diary>>>

    }

    interface Local {

        suspend fun getDiariesPagingLocal(limit: Int, offset: Int): List<Diary>
        suspend fun searchDiariesPagingLocal(query: String, limit: Int, offset: Int): List<Diary>
        suspend fun clearLocalData()

    }

}