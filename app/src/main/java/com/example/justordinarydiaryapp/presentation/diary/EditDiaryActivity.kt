package com.example.justordinarydiaryapp.presentation.diary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.justordinarydiaryapp.base.presentation.BaseActivity
import com.example.justordinarydiaryapp.databinding.ActivityEditDiaryBinding
import com.example.justordinarydiaryapp.model.Diary
import com.example.justordinarydiaryapp.utils.extension.setNavigationAsBack

class EditDiaryActivity : BaseActivity<ActivityEditDiaryBinding>() {

    private val diaryData by lazy { intent.getParcelableExtra<Diary>(KEY_TRANSFER_DIARY) }

    private val fragment by lazy { diaryData?.let { EditDiaryFragment.newInstance(it) } }

    override val bindingInflater: (LayoutInflater) -> ActivityEditDiaryBinding
        get() = ActivityEditDiaryBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setNavigationAsBack(this)
        binding.headerToolbar.toolbarTitle.text = "Edit Diary"
        initFragment()
    }

    private fun initFragment() {
        fragment?.let {
            supportFragmentManager.beginTransaction().replace(binding.flContainer.id, it).commit()
        }
    }

    companion object {

        private const val KEY_TRANSFER_DIARY = "KEY_TRANSFER_DIARY"

        fun launchIntent(context: Context, diary: Diary) {
            val intent = Intent(context, EditDiaryActivity::class.java)
            intent.putExtra(KEY_TRANSFER_DIARY, diary)
            context.startActivity(intent)
        }
    }
}