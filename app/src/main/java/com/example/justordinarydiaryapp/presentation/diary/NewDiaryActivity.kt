package com.example.justordinarydiaryapp.presentation.diary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.justordinarydiaryapp.base.presentation.BaseActivity
import com.example.justordinarydiaryapp.databinding.ActivityNewDiaryBinding
import com.example.justordinarydiaryapp.utils.extension.setNavigationAsBack

class NewDiaryActivity : BaseActivity<ActivityNewDiaryBinding>() {

    private val fragment by lazy { NewDiaryFragment.newInstance() }

    override val bindingInflater: (LayoutInflater) -> ActivityNewDiaryBinding
        get() = ActivityNewDiaryBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setNavigationAsBack(this)
        binding.headerToolbar.toolbarTitle.text = "Create New Diary"
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.flContainer.id, fragment).commit()
    }

    companion object {
        fun launchIntent(context: Context) {
            val intent = Intent(context, NewDiaryActivity::class.java)
            context.startActivity(intent)
        }
    }
}