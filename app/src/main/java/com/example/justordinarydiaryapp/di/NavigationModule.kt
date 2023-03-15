package com.example.justordinarydiaryapp.di

import com.example.justordinarydiaryapp.navigation.DashboardNavigation
import com.example.justordinarydiaryapp.navigation.DashboardNavigationImpl
import com.example.justordinarydiaryapp.navigation.SplashScreenNavigation
import com.example.justordinarydiaryapp.navigation.SplashScreenNavigationImpl
import org.koin.dsl.module

object NavigationModule {

    val modules = module {
        single<SplashScreenNavigation> { SplashScreenNavigationImpl() }
        single<DashboardNavigation> { DashboardNavigationImpl() }
    }

}