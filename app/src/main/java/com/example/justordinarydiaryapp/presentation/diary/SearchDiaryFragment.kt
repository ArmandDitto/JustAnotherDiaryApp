package com.example.justordinarydiaryapp.presentation.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justordinarydiaryapp.base.paging.LocalPagingLoadStateAdapter
import com.example.justordinarydiaryapp.base.presentation.BaseFragment
import com.example.justordinarydiaryapp.base.presentation.ProgressDialog
import com.example.justordinarydiaryapp.databinding.FragmentSearchDiaryBinding
import com.example.justordinarydiaryapp.navigation.DashboardNavigation
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import com.example.justordinarydiaryapp.utils.extension.goneView
import com.example.justordinarydiaryapp.utils.extension.onScrolledListener
import com.example.justordinarydiaryapp.utils.extension.setAutoNullAdapter
import com.example.justordinarydiaryapp.utils.extension.visibleView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchDiaryFragment : BaseFragment<FragmentSearchDiaryBinding>() {

    private val viewModel: DiaryViewModel by viewModel()
    private val localViewModel: DiaryLocalViewModel by viewModel()
    private lateinit var diaryLocalItemAdapter: DiaryLocalItemAdapter
    private var page = INIT_PAGE
    private val navigation: DashboardNavigation by inject()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchDiaryBinding
        get() = FragmentSearchDiaryBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
        fetchData(page)
    }

    private fun fetchData(loadPage: Int) {
        viewModel.fetchDiaries(loadPage)
        page += 1
    }

    private fun doSearch(query: String) {
        localViewModel.searchLocalDiaries(query)
    }

    private fun getLocalData() {
        localViewModel.loadLocalDiaries()
    }

    private fun initObserver() {
        viewModel.diariesLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    ProgressDialog.show(requireContext())
                }

                is ResultWrapper.Success -> {
                    if (!it.value.data.isNullOrEmpty()) {
                        fetchData(page)
                    } else {
                        ProgressDialog.dismiss()
                        getLocalData()
                        page = INIT_PAGE
                    }
                }

                is ResultWrapper.Error -> {
                    ProgressDialog.dismiss()
                    showErrorDialog(
                        desc = it.message,
                        onDismiss = { requireActivity().finish() },
                        isCancelable = true
                    )
                }
            }
        }

        localViewModel.diariesLocalLiveData.observe(viewLifecycleOwner) {
            diaryLocalItemAdapter.submitData(this.lifecycle, it)
        }

        localViewModel.searchDiariesLocalLiveData.observe(viewLifecycleOwner) {
            diaryLocalItemAdapter.submitData(this.lifecycle, it)
        }
    }

    private fun initView() {

        binding.txtSearch.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isEmpty()) {
                localViewModel.loadLocalDiaries()
            }
        }

        binding.txtSearchLayout.setEndIconOnClickListener {
            if (binding.txtSearch.text.isNullOrBlank()) {
                localViewModel.loadLocalDiaries()
            } else {
                doSearch(binding.txtSearch.text.toString())
            }
        }

        binding.txtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (binding.txtSearch.text.isNullOrBlank()) {
                    localViewModel.loadLocalDiaries()
                } else {
                    doSearch(binding.txtSearch.text.toString())
                }
            }
            false
        }

        binding.cvProfile.setOnClickListener {
            showConfimationDialog(
                title = "Warning",
                desc = "Are you sure do you want to log out ?",
                onPositiveBtnClick = {
                    localViewModel.clearLocalDiaries()
                    navigation.onLogOut(requireActivity())
                }
            )
        }

        binding.btnCompose.setOnClickListener {
            NewDiaryActivity.launchIntent(requireContext())
        }

        diaryLocalItemAdapter = DiaryLocalItemAdapter().apply {
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
                            onPositiveBtnClick = { fetchData(page) },
                            isCancelable = true
                        )
                    }
                }
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
            if (!binding.txtSearch.text.isNullOrBlank()) {
                doSearch(binding.txtSearch.text.toString())
            } else {
                fetchData(page)
            }
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

        private const val INIT_PAGE = 1

        fun newInstance(): SearchDiaryFragment {
            return SearchDiaryFragment().apply {
                val args = Bundle()
                arguments = args
            }
        }
    }
}