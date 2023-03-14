package com.example.justordinarydiaryapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.justordinarydiaryapp.data.dao.DiaryDao
import com.example.justordinarydiaryapp.data.entity.DiaryEntity

@Database(
    entities = [
        DiaryEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract val diaryDao: DiaryDao

}