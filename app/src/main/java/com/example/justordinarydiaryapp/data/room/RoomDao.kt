package com.example.justordinarydiaryapp.data.room

import com.example.justordinarydiaryapp.data.dao.DiaryDao

class RoomDao(private val appDataBase: AppDatabase) {

    fun diaryDao(): DiaryDao = appDataBase.diaryDao

}