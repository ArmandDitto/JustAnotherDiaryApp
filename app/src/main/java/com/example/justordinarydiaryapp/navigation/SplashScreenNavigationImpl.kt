package com.example.justordinarydiaryapp.navigation

import android.app.Activity
import com.example.justordinarydiaryapp.presentation.home.HomeActivity
import com.example.justordinarydiaryapp.presentation.login.LoginActivity
import com.example.justordinarydiaryapp.utils.extension.showDefaultToast
import com.example.justordinarydiaryapp.utils.preference.PreferencesHelper

class SplashScreenNavigationImpl : SplashScreenNavigation {

    override fun onSplashScreenComplete(activity: Activity) {
        val auth = PreferencesHelper.authToken

        if (auth?.isNotEmpty() == true) {
            HomeActivity.launchIntent(activity)
            activity.finish()
        } else {
            LoginActivity.launchIntent(activity)
            activity.finish()
        }
    }

}