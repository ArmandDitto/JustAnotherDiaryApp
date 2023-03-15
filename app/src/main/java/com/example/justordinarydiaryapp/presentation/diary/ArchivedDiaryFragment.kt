package com.example.justordinarydiaryapp.presentation.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justordinarydiaryapp.base.paging.LocalPagingLoadStateAdapter
import com.example.justordinarydiaryapp.base.presentation.BaseFragment
import com.example.justordinarydiaryapp.base.presentation.ProgressDialog
import com.example.justordinarydiaryapp.databinding.FragmentArchivedDiaryBinding
import com.example.justordinarydiaryapp.navigation.DashboardNavigation
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import com.example.justordinarydiaryapp.utils.extension.setAutoNullAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArchivedDiaryFragment : BaseFragment<FragmentArchivedDiaryBinding>() {

    private val localViewModel: DiaryLocalViewModel by viewModel()
    private val viewModel: DiaryViewModel by viewModel()
    private lateinit var diaryLocalItemAdapter: DiaryLocalItemAdapter

    private val navigation: DashboardNavigation by inject()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentArchivedDiaryBinding
        get() = FragmentArchivedDiaryBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        getLocalData()
    }

    private fun getLocalData() {
        localViewModel.loadArchivedDiaries()
        localViewModel.getArchivedDiaryCount()
    }

    private fun initObserver() {
        viewModel.unarchiveDiaryLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    ProgressDialog.show(requireContext())
                }

                is ResultWrapper.Success -> {
                    ProgressDialog.dismiss()
                    showSuccessDialog(
                        desc = "Diary Unarchived Successfully",
                    )
                    getLocalData()
                }

                is ResultWrapper.Error -> {
                    ProgressDialog.dismiss()
                    showErrorDialog(
                        desc = it.message,
                        btnPositiveText = "Ok",
                    )
                }
            }
        }

        localViewModel.archivedDiariesCountLiveData.observe(viewLifecycleOwner) {
            binding.tvDiaryCount.text = "Diary Count: $it"
        }

        localViewModel.archivedDiariesLiveData.observe(viewLifecycleOwner) {
            diaryLocalItemAdapter.submitData(this.lifecycle, it)
        }
    }

    private fun initView() {

        diaryLocalItemAdapter = DiaryLocalItemAdapter(true).apply {
            onUnarchived = {
                showConfimationDialog(
                    desc = "Do you really want to unarchive this diary ?",
                    onPositiveBtnClick = { it.id?.let { id -> viewModel.unarchiveDiary(id) } },
                )
            }
        }

        binding.rvContent.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setAutoNullAdapter(
                diaryLocalItemAdapter.withLoadStateFooter(
                    footer = LocalPagingLoadStateAdapter()
                )
            )
        }

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            getLocalData()
        }

        binding.cvLogout.setOnClickListener {
            showConfimationDialog(
                title = "Warning",
                desc = "Are you sure do you want to log out and remove all archived diaries ?",
                onPositiveBtnClick = {
                    navigation.onLogOut(requireActivity())
                    localViewModel.clearLocalDiaries()
                    localViewModel.clearLocalArchivedDiaries()
                }
            )
        }

    }

    companion object {

        fun newInstance(): ArchivedDiaryFragment {
            return ArchivedDiaryFragment().apply {
                val args = Bundle()
                arguments = args
            }
        }
    }
}