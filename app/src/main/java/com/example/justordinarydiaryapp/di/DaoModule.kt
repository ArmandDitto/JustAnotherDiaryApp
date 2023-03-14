package com.example.justordinarydiaryapp.di

import com.example.justordinarydiaryapp.data.room.RoomDao
import org.koin.dsl.module

object DaoModule {

    val modules = module {
        single { RoomDao(get()).diaryDao() }
    }

}