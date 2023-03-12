package com.example.justordinarydiaryapp.di

import com.example.justordinarydiaryapp.data.repository.AuthRepository
import com.example.justordinarydiaryapp.data.repository.AuthRepositoryImpl
import org.koin.dsl.module

object RepositoryModule {

    val modules = module {
        single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    }

}