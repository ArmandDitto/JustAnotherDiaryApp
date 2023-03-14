package com.example.justordinarydiaryapp.presentation.diary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.justordinarydiaryapp.base.presentation.BaseActivity
import com.example.justordinarydiaryapp.databinding.ActivityDetailDiaryBinding
import com.example.justordinarydiaryapp.utils.extension.setNavigationAsBack

class DiaryDetailActivity : BaseActivity<ActivityDetailDiaryBinding>() {

    private val diaryId: String by lazy { intent.getStringExtra(KEY_DIARY_ID) ?: "" }

    private val fragment by lazy { DiaryDetailFragment.newInstance(diaryId) }

    override val bindingInflater: (LayoutInflater) -> ActivityDetailDiaryBinding
        get() = ActivityDetailDiaryBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setNavigationAsBack(this)
        binding.headerToolbar.toolbarTitle.text = "Diary"
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.flContainer.id, fragment).commit()
    }

    companion object {

        private const val KEY_DIARY_ID = "KEY_DIARY_ID"

        fun launchIntent(context: Context, diaryId: String) {
            val intent = Intent(context, DiaryDetailActivity::class.java)
            intent.putExtra(KEY_DIARY_ID, diaryId)
            context.startActivity(intent)
        }
    }
}