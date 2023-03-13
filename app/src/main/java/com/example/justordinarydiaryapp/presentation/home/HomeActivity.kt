package com.example.justordinarydiaryapp.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.justordinarydiaryapp.R
import com.example.justordinarydiaryapp.base.presentation.BaseActivity
import com.example.justordinarydiaryapp.databinding.ActivityHomeBinding
import com.example.justordinarydiaryapp.presentation.diary.DiaryListFragment


class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    var prevMenuItem: MenuItem? = null

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_home -> viewPager.currentItem = 0
//                    R.id.menu_search -> viewPager.currentItem = 1
//                    R.id.menu_profile -> viewPager.currentItem = 2
                }
                false
            }
        }

        setupViewPager(binding.viewPager)

        binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (prevMenuItem == null) {
                    binding.bottomNavigation.menu.getItem(0).isChecked = false
                }
                binding.bottomNavigation.menu.getItem(position).isChecked = true
                prevMenuItem = binding.bottomNavigation.menu.getItem(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = HomePagerAdapter(supportFragmentManager)
        adapter.addFragment(DiaryListFragment())
        adapter.addFragment(DiaryListFragment())
        adapter.addFragment(DiaryListFragment())
        viewPager.adapter = adapter
    }

    companion object {
        fun launchIntent(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}