package com.example.justordinarydiaryapp.di

import com.example.justordinarydiaryapp.data.repository.AuthRepository
import com.example.justordinarydiaryapp.data.repository.AuthRepositoryImpl
import com.example.justordinarydiaryapp.data.repository.DiaryRepository
import com.example.justordinarydiaryapp.data.repository.DiaryRepositoryImpl
import org.koin.dsl.module

object RepositoryModule {

    val modules = module {
        single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
        single<DiaryRepository> { DiaryRepositoryImpl(get(),get()) }
    }

}