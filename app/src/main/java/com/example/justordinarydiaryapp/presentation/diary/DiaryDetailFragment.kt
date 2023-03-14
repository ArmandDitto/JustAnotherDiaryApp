package com.example.justordinarydiaryapp.presentation.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.justordinarydiaryapp.base.presentation.BaseFragment
import com.example.justordinarydiaryapp.base.presentation.ProgressDialog
import com.example.justordinarydiaryapp.databinding.FragmentDetailDiaryBinding
import com.example.justordinarydiaryapp.model.Diary
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import com.example.justordinarydiaryapp.utils.DateTimeHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiaryDetailFragment : BaseFragment<FragmentDetailDiaryBinding>() {

    private val viewModel: DiaryViewModel by viewModel()

    private val diaryId by lazy { arguments?.getString(KEY_DIARY_ID) ?: "" }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailDiaryBinding
        get() = FragmentDetailDiaryBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        fetchData(diaryId)
    }

    private fun fetchData(diaryId: String) {
        viewModel.getDiaryDetail(diaryId)
    }

    private fun initObserver() {
        viewModel.diaryDetailLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    ProgressDialog.show(requireContext())
                }

                is ResultWrapper.Success -> {
                    ProgressDialog.dismiss()
                    onGetDetailSuccess(it.value)
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
    }

    private fun onGetDetailSuccess(diary: Diary) {
        binding.apply {
            tvTitle.text = diary.title
            tvContent.text = diary.content

            val dateCreated = DateTimeHelper.getStringFromString(
                sourceString = diary.createdAt,
                sourceFormat = DateTimeHelper.FORMAT_YEAR_MONTH_DAY_HOUR24_MINUTE_SECOND_MILLISECOND_TIMEZONE,
                targetFormat = DateTimeHelper.FORMAT_DAY_MONTHNAME_YEAR_HOUR24_MINUTE
            )
            tvDateCreated.text = String.format("Created at %s", dateCreated)

            val dateModified = DateTimeHelper.getStringFromString(
                sourceString = diary.updatedAt,
                sourceFormat = DateTimeHelper.FORMAT_YEAR_MONTH_DAY_HOUR24_MINUTE_SECOND_MILLISECOND_TIMEZONE,
                targetFormat = DateTimeHelper.FORMAT_DAY_MONTHNAME_YEAR_HOUR24_MINUTE
            )
            tvDateModified.text = String.format("Last modified: %s", dateModified)
        }

        binding.cvEdit.setOnClickListener {
            EditDiaryActivity.launchIntent(requireContext(), diary)
        }
    }

    companion object {

        private const val KEY_DIARY_ID = "KEY_DIARY_ID"

        fun newInstance(diaryId: String): DiaryDetailFragment {
            return DiaryDetailFragment().apply {
                val args = Bundle()
                args.putString(KEY_DIARY_ID, diaryId)
                arguments = args
            }
        }
    }
}