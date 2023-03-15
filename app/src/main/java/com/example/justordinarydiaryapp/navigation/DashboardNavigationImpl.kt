package com.example.justordinarydiaryapp.navigation

import android.app.Activity
import com.example.justordinarydiaryapp.presentation.login.LoginActivity
import com.example.justordinarydiaryapp.utils.Constants
import com.example.justordinarydiaryapp.utils.preference.PreferencesHelper

class DashboardNavigationImpl : DashboardNavigation {

    override fun onLogOut(activity: Activity) {
        PreferencesHelper.customPrefs(Constants.PREF_NAME)
            .edit()
            .remove(Constants.PREF_AUTH_TOKEN)
            .apply()
        activity.finish()
        LoginActivity.launchIntent(activity)
    }

}