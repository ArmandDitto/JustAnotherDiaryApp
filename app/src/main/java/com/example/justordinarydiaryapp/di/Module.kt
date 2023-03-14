package com.example.justordinarydiaryapp.di

import androidx.room.Room
import com.example.justordinarydiaryapp.data.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object Module {

    private val appModules = module {
        single { Dispatchers.IO }
    }

    private val databaseModule = module {
        single {
            Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app_room_db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    fun getAll() = listOf(
        appModules,
        databaseModule,
        DaoModule.modules,
        NetworkModule.modules,
        RepositoryModule.modules,
        ViewModelModule.modules,
        NavigationModule.modules,
    )

}