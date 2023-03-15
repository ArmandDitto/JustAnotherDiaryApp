package com.example.justordinarydiaryapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.justordinarydiaryapp.data.entity.ArchivedDiaryEntity
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArchivedDiary(data: ArchivedDiaryEntity)

    @Delete
    suspend fun deleteArchivedDiary(data: ArchivedDiaryEntity)

    @Query("SELECT * FROM ${ArchivedDiaryEntity.TABLE_NAME} LIMIT :limit OFFSET :offset")
    suspend fun getAllArchivedDiary(limit: Int, offset: Int): List<ArchivedDiaryEntity>

    @Query("DELETE FROM ${ArchivedDiaryEntity.TABLE_NAME}")
    suspend fun clearAllArchivedDiary()

    @Query("SELECT COUNT(id) FROM ${ArchivedDiaryEntity.TABLE_NAME}")
    suspend fun getArchivedDiaryCount() : Int

}