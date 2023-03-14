package com.example.justordinarydiaryapp.di

import com.example.justordinarydiaryapp.data.repository.AuthRepository
import com.example.justordinarydiaryapp.data.repository.AuthRepositoryImpl
import com.example.justordinarydiaryapp.data.repository.DiaryLocalRepositoryImpl
import com.example.justordinarydiaryapp.data.repository.DiaryRemoteRepositoryImpl
import com.example.justordinarydiaryapp.data.repository.DiaryRepository
import org.koin.dsl.module

object RepositoryModule {

    val modules = module {
        single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
        single<DiaryRepository.Remote> { DiaryRemoteRepositoryImpl(get(), get(), get()) }
        single<DiaryRepository.Local> { DiaryLocalRepositoryImpl(get()) }
    }

}