package com.example.justordinarydiaryapp

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.justordinarydiaryapp.di.Module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JustOrdinaryDiaryApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        instance = this
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@JustOrdinaryDiaryApp)
            modules(Module.getAll())
        }
    }

    companion object {
        lateinit var instance: JustOrdinaryDiaryApp
            private set

        fun getApp(): JustOrdinaryDiaryApp {
            return instance
        }

        fun getContext(): Context {
            return instance
        }
    }
}