package com.example.justordinarydiaryapp.presentation.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justordinarydiaryapp.base.paging.PagingLoadStateAdapter
import com.example.justordinarydiaryapp.base.presentation.BaseFragment
import com.example.justordinarydiaryapp.base.presentation.ProgressDialog
import com.example.justordinarydiaryapp.databinding.FragmentDiaryListBinding
import com.example.justordinarydiaryapp.utils.extension.goneView
import com.example.justordinarydiaryapp.utils.extension.onScrolledListener
import com.example.justordinarydiaryapp.utils.extension.setAutoNullAdapter
import com.example.justordinarydiaryapp.utils.extension.visibleView
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiaryListFragment : BaseFragment<FragmentDiaryListBinding>() {

    private val viewModel: DiaryViewModel by viewModel()
    private lateinit var diaryItemAdapter: DiaryItemAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDiaryListBinding
        get() = FragmentDiaryListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
        fetchData()
    }

    private fun fetchData() {
        viewModel.fetchDiariesPaging()
    }

    private fun initObserver() {
        viewModel.diaryPagingLiveData.observe(viewLifecycleOwner) {
            diaryItemAdapter.submitData(this.lifecycle, it)
        }
    }

    private fun initView() {
        binding.btnCompose.setOnClickListener {
            NewDiaryActivity.launchIntent(requireContext())
        }

        diaryItemAdapter = DiaryItemAdapter(requireContext()).apply {
            onRootClick = {
                it.id?.let { id ->
                    DiaryDetailActivity.launchIntent(requireContext(), id)
                }
            }
            addLoadStateListener { loadState ->
                when (loadState.source.refresh) {
                    is LoadState.Loading -> {
                        if (this.itemCount == 0) {
                            ProgressDialog.show(requireContext())
                        } else {
                            ProgressDialog.show(requireContext())
                        }
                    }

                    is LoadState.NotLoading -> {
                        ProgressDialog.dismiss()
                    }

                    is LoadState.Error -> {
                        showErrorDialog(
                            onPositiveBtnClick = { fetchData() },
                            isCancelable = true
                        )
                    }
                }
            }
        }

        binding.rvContent.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setAutoNullAdapter(diaryItemAdapter.withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter().apply {
                    onRetry = { diaryItemAdapter.refresh() }
                },
                footer = PagingLoadStateAdapter().apply {
                    onRetry = { diaryItemAdapter.retry() }
                }
            ))
        }

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            fetchData()
        }

        binding.rvContent.onScrolledListener(viewLifecycleOwner) { _, _, dy ->
            when {
                dy > 0 -> binding.btnLaunchToTop.visibleView()
                dy < 0 -> binding.btnLaunchToTop.goneView()
            }
        }

        binding.btnLaunchToTop.setOnClickListener {
            binding.rvContent.smoothScrollToPosition(0)
        }
    }

    companion object {
        fun newInstance(): DiaryListFragment {
            return DiaryListFragment().apply {
                val args = Bundle()
                arguments = args
            }
        }
    }
}