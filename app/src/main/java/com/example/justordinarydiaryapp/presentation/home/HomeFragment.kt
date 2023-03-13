package com.example.justordinarydiaryapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.justordinarydiaryapp.base.presentation.BaseFragment
import com.example.justordinarydiaryapp.databinding.FragmentHomeBinding
import com.example.justordinarydiaryapp.presentation.login.LoginActivity
import com.example.justordinarydiaryapp.utils.Constants
import com.example.justordinarydiaryapp.utils.preference.PreferencesHelper

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtToken.setOnClickListener { showToast(PreferencesHelper.authToken.toString()) }
        binding.txtUser.setOnClickListener {
            PreferencesHelper.customPrefs(Constants.PREF_NAME).edit().clear().apply()
            LoginActivity.launchIntent(requireContext())
        }

    }

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment().apply {
                val args = Bundle()
                arguments = args
            }

        }
    }

}