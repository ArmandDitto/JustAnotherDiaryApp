package com.example.justordinarydiaryapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.justordinarydiaryapp.data.entity.DiaryEntity

@Dao
interface DiaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: DiaryEntity)

    @Query("SELECT * FROM ${DiaryEntity.TABLE_NAME}")
    suspend fun getAllDiary(): List<DiaryEntity>

    @Query("SELECT * FROM ${DiaryEntity.TABLE_NAME} LIMIT :limit OFFSET :offset")
    suspend fun getDiaryPagedList(limit: Int, offset: Int): List<DiaryEntity>

    @Query("SELECT * FROM ${DiaryEntity.TABLE_NAME} WHERE title LIKE '%' || :searchQuery || '%' LIMIT :limit OFFSET :offset")
    suspend fun searchDiaryPagedList(searchQuery: String, limit: Int, offset: Int): List<DiaryEntity>

    @Query("DELETE FROM ${DiaryEntity.TABLE_NAME}")
    suspend fun clearData()

}