package com.example.justordinarydiaryapp.di

import com.example.justordinarydiaryapp.presentation.diary.DiaryViewModel
import com.example.justordinarydiaryapp.presentation.login.LoginViewModel
import com.example.justordinarydiaryapp.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {

    val modules = module {
        viewModel { LoginViewModel(get()) }
        viewModel { RegisterViewModel(get()) }
        viewModel { DiaryViewModel(get()) }
    }

}