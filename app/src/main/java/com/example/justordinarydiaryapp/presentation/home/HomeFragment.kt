package com.example.justordinarydiaryapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.justordinarydiaryapp.base.presentation.BaseFragment
import com.example.justordinarydiaryapp.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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