package com.example.justordinarydiaryapp.data.repository

import com.example.justordinarydiaryapp.data.dao.DiaryDao
import com.example.justordinarydiaryapp.data.entity.ArchivedDiaryEntity
import com.example.justordinarydiaryapp.data.entity.DiaryEntity
import com.example.justordinarydiaryapp.model.Diary

class DiaryLocalRepositoryImpl(
    private val diaryDao: DiaryDao
) : DiaryRepository.Local {

    override suspend fun getDiariesPagingLocal(limit: Int, offset: Int): List<Diary> {
        val result = diaryDao.getDiaryPagedList(limit, offset)
        val diaryList = mutableListOf<Diary>()
        result.forEach {
            val diary = DiaryEntity.ModelMapper.toDiary(it)
            diaryList.add(diary)
        }
        return diaryList
    }

    override suspend fun searchDiariesPagingLocal(
        query: String,
        limit: Int,
        offset: Int
    ): List<Diary> {
        val result = diaryDao.searchDiaryPagedList(query, limit, offset)
        val diaryList = mutableListOf<Diary>()
        result.forEach {
            val diary = DiaryEntity.ModelMapper.toDiary(it)
            diaryList.add(diary)
        }
        return diaryList
    }

    override suspend fun clearAllLocalDiary() {
        return diaryDao.clearData()
    }

    override suspend fun getAllArchivedDiary(limit: Int, offset: Int): List<Diary> {
        val result = diaryDao.getAllArchivedDiary(limit, offset)
        val diaryList = mutableListOf<Diary>()
        result.forEach {
            val diary = ArchivedDiaryEntity.ModelMapper.toDiary(it)
            diaryList.add(diary)
        }
        return diaryList
    }

    override suspend fun clearAllArchivedDiary() {
        return diaryDao.clearAllArchivedDiary()
    }

    override suspend fun getArchivedDiaryCount(): Int {
        return diaryDao.getArchivedDiaryCount()
    }

}