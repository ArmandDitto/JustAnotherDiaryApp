package com.example.justordinarydiaryapp.presentation.diary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.justordinarydiaryapp.base.presentation.BaseActivity
import com.example.justordinarydiaryapp.databinding.ActivityArchivedDiaryBinding
import com.example.justordinarydiaryapp.navigation.DashboardNavigation
import com.example.justordinarydiaryapp.utils.extension.setNavigationAsBack
import com.example.justordinarydiaryapp.utils.extension.visibleView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArchivedDiaryActivity : BaseActivity<ActivityArchivedDiaryBinding>() {

    private val fragment by lazy { ArchivedDiaryFragment.newInstance() }

    override val bindingInflater: (LayoutInflater) -> ActivityArchivedDiaryBinding
        get() = ActivityArchivedDiaryBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setNavigationAsBack(this)
        binding.headerToolbar.toolbarTitle.text = "Archived Diary"

        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.flContainer.id, fragment).commit()
    }

    companion object {
        fun launchIntent(context: Context) {
            val intent = Intent(context, ArchivedDiaryActivity::class.java)
            context.startActivity(intent)
        }
    }
}