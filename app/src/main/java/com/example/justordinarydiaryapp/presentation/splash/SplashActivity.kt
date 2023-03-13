package com.example.justordinarydiaryapp.presentation.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.example.justordinarydiaryapp.base.presentation.BaseActivity
import com.example.justordinarydiaryapp.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val fragment by lazy { SplashFragment.newInstance() }

    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.flContainer.id, fragment).commit()
    }

    companion object {
        fun launchIntent(context: Context) {
            val intent = Intent(context, SplashActivity::class.java)
            context.startActivity(intent)
        }
    }
}