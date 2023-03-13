package com.example.justordinarydiaryapp.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.justordinarydiaryapp.base.presentation.BaseFragment
import com.example.justordinarydiaryapp.databinding.FragmentSplashBinding
import com.example.justordinarydiaryapp.navigation.SplashScreenNavigation
import org.koin.android.ext.android.inject

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val navigation: SplashScreenNavigation by inject()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            navigation.onSplashScreenComplete(requireActivity())
        }, 3000)

    }

    companion object {
        fun newInstance(): SplashFragment {
            return SplashFragment().apply {
                val args = Bundle()
                arguments = args
            }
        }
    }
}