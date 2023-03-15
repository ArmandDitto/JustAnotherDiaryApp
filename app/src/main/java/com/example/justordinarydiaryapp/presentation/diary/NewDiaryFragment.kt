package com.example.justordinarydiaryapp.presentation.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.justordinarydiaryapp.base.presentation.BaseFragment
import com.example.justordinarydiaryapp.base.presentation.ProgressDialog
import com.example.justordinarydiaryapp.databinding.FragmentNewDiaryBinding
import com.example.justordinarydiaryapp.model.request.DiaryRequest
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import com.example.justordinarydiaryapp.presentation.home.HomeActivity
import com.example.justordinarydiaryapp.utils.DateTimeHelper
import com.example.justordinarydiaryapp.utils.ValidationHelper
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class NewDiaryFragment : BaseFragment<FragmentNewDiaryBinding>() {

    private val viewModel: DiaryViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNewDiaryBinding
        get() = FragmentNewDiaryBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initView()
    }

    private fun initObserver() {
        viewModel.createNewDiaryLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    ProgressDialog.show(requireContext())
                }

                is ResultWrapper.Success -> {
                    ProgressDialog.dismiss()
                    showSuccessDialog(
                        desc = "Diary Saved Successfully",
                        onDismiss = { HomeActivity.launchIntent(requireContext()) }
                    )
                }

                is ResultWrapper.Error -> {
                    ProgressDialog.dismiss()
                    showErrorDialog(
                        desc = it.message,
                        onPositiveBtnClick = { sendData() }
                    )
                }
            }
        }
    }

    private fun initView() {

        binding.txtContentDiary.doOnTextChanged { text, _, _, _ ->
            val characterCount = text?.length
            val maxLength = 500
            if (characterCount != null) {
                if (characterCount <= maxLength) {
                    binding.tvCount.text = String.format("%d/%d", characterCount, maxLength)
                }
            }
        }

        val text: String? = DateTimeHelper.getStringFromCalendar(
            Calendar.getInstance(),
            DateTimeHelper.FORMAT_DAY_MONTHNAME_YEAR_HOUR24_MINUTE
        )
        binding.tvCurrentTime.text = text

        binding.cvSave.setOnClickListener {
            if (isDataValid) {
                showConfimationDialog(
                    desc = "Do you really want to save this diary ?",
                    onPositiveBtnClick = { sendData() },
                )
            } else {
                showToast("Please fill title and content")
            }
        }
    }

    private fun sendData() {
        viewModel.createNewDiary(
            DiaryRequest(
                binding.txtTitleDiary.text.toString(),
                binding.txtContentDiary.text.toString()
            )
        )
    }

    private val isDataValid: Boolean
        get() {
            binding.apply {
                var isValid = true
                txtTitleDiary.clearFocus()
                txtContentDiary.clearFocus()

                if (ValidationHelper.isBlank(txtTitleDiary)) {
                    txtTitleDiary.requestFocus()
                    isValid = false
                } else if (ValidationHelper.isBlank(txtContentDiary)) {
                    txtContentDiary.requestFocus()
                    isValid = false
                }

                return isValid
            }
        }

    companion object {
        fun newInstance(): NewDiaryFragment {
            return NewDiaryFragment().apply {
                val args = Bundle()
                arguments = args
            }
        }
    }
}